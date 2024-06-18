package org.app.serviceusers.management.users.infrastructure.configurations.security.user;

import org.app.serviceusers.management.users.application.ports.inputs.IUserInputRepository;
import org.app.serviceusers.management.users.infrastructure.mappers.UserEntityMapper;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final IUserInputRepository inputRepository;

    private final UserEntityMapper mapper;


    public UserDetailsServiceImpl(IUserInputRepository inputRepository, UserEntityMapper mapper) {
        this.inputRepository = inputRepository;
        this.mapper = mapper;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return new UserDetailsImpl(mapper.toEntity(inputRepository.findByEmail(username)));
    }

}