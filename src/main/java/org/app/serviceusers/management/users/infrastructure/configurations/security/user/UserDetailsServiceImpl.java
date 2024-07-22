package org.app.serviceusers.management.users.infrastructure.configurations.security.user;

import org.app.serviceusers.management.users.application.ports.outputs.ICredentialPortOutputService;
import org.app.serviceusers.management.users.application.ports.outputs.IUserPortOutputService;
import org.app.serviceusers.management.users.infrastructure.mappers.MapperInfrastructureFactory;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final ICredentialPortOutputService port;

    private final MapperInfrastructureFactory mapper;

    public UserDetailsServiceImpl(ICredentialPortOutputService port, MapperInfrastructureFactory mapper) {
        this.port = port;
        this.mapper = mapper;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return new UserDetailsImpl(mapper.getUserMapper().toEntity(port.findByEmail(username).get()));
    }

}