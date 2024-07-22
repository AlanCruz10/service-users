pipeline {
    agent any
    environment {
        WORKSPACE_DIR = "${env.WORKSPACE}"
        DOCKER_IMAGE_NAME = "lang-speak-users-service-prod-deploy"
        DOCKER_IMAGE_TAG = "${DOCKER_IMAGE_NAME}:${env.BUILD_NUMBER}"
        REMOTE_USER = "ubuntu"
        REMOTE_HOST = "100.29.50.131"
    }
    stages {
        stage('Checkout') {
            steps {
                // Get some code from a GitHub repository
                checkout scm
            }
        }
        stage('Prepare Configurations Pre-Deploy') {
            steps {
                script {
                    // Copy the application-dev.properties file to the src/main/resources directory
                    withCredentials([file(credentialsId: 'file-application-properties', variable: 'FILE_APPLICATION_PROPERTIES')]) {
                        script {
                            sh 'sudo cp $FILE_APPLICATION_PROPERTIES $WORKSPACE_DIR/src/main/resources/application-dev.properties'
                        }
                    }
                }
            }
        }
        stage('Build') {
            steps {
                script {
                    // Build the application
                    docker.build(DOCKER_IMAGE_TAG)
                }
            }
        }
        stage('Test') {
            steps {
                script {
                    // Run the tests
                    sh 'mvn test'
                }
            }
        }
        stage('Deploy') {
            steps {
                script {
                    // Deploy the application
                    sshagent(['ssh-credetials-lang-speak-users-prod-ec2']) {
                        sh """
                        ssh ${REMOTE_USER}@${REMOTE_HOST} 'docker stop ${DOCKER_IMAGE_NAME} || true && docker rm ${DOCKER_IMAGE_NAME} || true'
                        ssh ${REMOTE_USER}@${REMOTE_HOST} 'docker run --name ${DOCKER_IMAGE_NAME} -d -p 8081:8081 ${DOCKER_IMAGE_TAG}'
                        """
                    }
                }
            }
        }

    }
    post {
        failure {
            script {
                notifyBuild(currentBuild.result)
            }
        }
    }
}

def notifyBuild(String buildStatus = 'STARTED') {

    // build status of null means successful
    buildStatus = buildStatus ?: 'SUCCESSFUL'

    // Default values
    def colorName = 'RED'
    def colorCode = '#FF0000'
    def now = new Date()

    // message
    def subject = "${buildStatus}, Job: ${env.JOB_NAME} BACKEND - Deployment Sequence: [${env.BUILD_NUMBER}] "
    def summary = "${subject} - Check On: (${env.BUILD_URL}) - Time: ${now}"
    def subject_email = "Spring boot Deployment"
    def details = """
        <p>${buildStatus} JOB </p>
        <p>Job: ${env.JOB_NAME} - Deployment Sequence: [${env.BUILD_NUMBER}] - Time: ${now}</p>
        <p>Check console output at "<a href="${env.BUILD_URL}">${env.JOB_NAME}</a>"</p>
        """

    // Email notification
    emailext (
        to: "213376@ids.upchiapas.edu.mx",
        subject: subject_email,
        body: details,
        recipientProviders: [[$class: 'DevelopersRecipientProvider']]
    )

}