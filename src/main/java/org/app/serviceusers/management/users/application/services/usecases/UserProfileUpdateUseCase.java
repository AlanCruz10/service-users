package org.app.serviceusers.management.users.application.services.usecases;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import jakarta.annotation.PostConstruct;
import org.apache.commons.io.FilenameUtils;
import org.app.serviceusers.management.users.application.ports.inputs.IHash256Encoder;
import org.app.serviceusers.management.users.application.ports.inputs.IUserProfilePortInputService;
import org.app.serviceusers.management.users.domain.models.UserProfile;
import org.app.serviceusers.management.users.infrastructure.exceptions.PreconditionFailedException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Objects;
import java.util.UUID;

@Service
public class UserProfileUpdateUseCase implements IUserProfilePortInputService {

    private AmazonS3 s3client;

    @Value(value = "${aws.bucket-name}")
    private String bucketName;

    @Value(value = "${aws.endpoint-url}")
    private String endpointUrl;

    @Value(value = "${aws.access-key}")
    private String accessKey;

    @Value(value = "${aws.secret-key}")
    private String secretKey;

    private final IHash256Encoder hash256Encoder;

    @PostConstruct
    private void initializeAmazon() {
        AWSCredentials credentials = new BasicAWSCredentials(accessKey, secretKey);
        s3client = AmazonS3ClientBuilder
                .standard()
                .withCredentials(new AWSStaticCredentialsProvider(credentials))
                .withRegion(Regions.US_EAST_1)
                .build();
    }

    public UserProfileUpdateUseCase(IHash256Encoder hash256Encoder) {
        this.hash256Encoder = hash256Encoder;
    }

    @Override
    public String uploadProfilePicture(UserProfile userProfile, MultipartFile profilePicture) {
        if (validateFileExtension(profilePicture)) {
            String urlDirection = "images/"
                    + userProfile.getUser().getCredential().getEmail()
                    + "/" + hash256Encoder.encodeToString(userProfile.getUsername().replace(" ", "_"))
                    + "/" + "profile-pictures/";
            String fileName = filePath(urlDirection, profilePicture);
            uploadFileToS3Bucket(fileName, convertMultiPartToFile(profilePicture));
            convertMultiPartToFile(profilePicture).delete();
            return fileURL(fileName);
        }
        throw new PreconditionFailedException("Invalid file extension");
    }

    private Boolean validateFileExtension(MultipartFile file) {
        String extension = FilenameUtils.getExtension(file.getOriginalFilename());
        if (extension == null) return Boolean.FALSE;
        extension = extension.toLowerCase();
        return (extension.equals("jpeg") || extension.equals("jpg") || extension.equals("png")) ? Boolean.TRUE : Boolean.FALSE;
    }

    private String generateFileName(MultipartFile multiPart) {
        return UUID.randomUUID().toString() + "_" + Objects.requireNonNull(multiPart.getOriginalFilename()).replaceAll("[^a-zA-Z0-9\\.\\-]", "_");
    }

    private String filePath(String urlDirection, MultipartFile multipartFile){
        return urlDirection + generateFileName(multipartFile);
    }

    private String fileURL(String fileName){
        return "https://" + bucketName + "." + endpointUrl + "/" + fileName;
    }

    private File convertMultiPartToFile(MultipartFile file) {
        File convFile = null;
        try {
            convFile = File.createTempFile("temp", file.getOriginalFilename());
            FileOutputStream fos = new FileOutputStream(convFile);
            fos.write(file.getBytes());
            fos.close();
            return convFile;
        }catch (IOException e){
            if (convFile != null) convFile.delete();
            throw new PreconditionFailedException("Error converting file, " + e.getMessage());
        }
    }

    private void uploadFileToS3Bucket(String fileName, File file) {
        s3client.putObject(new PutObjectRequest(bucketName, fileName, file)
                .withCannedAcl(CannedAccessControlList.PublicRead));
    }

}