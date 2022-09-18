package com.example.clip.service;

import com.example.clip.domain.Security;
import com.example.clip.repos.SecurityRepository;
import java.util.Collections;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


@Service
@Slf4j
public class JwtUserDetailsService implements UserDetailsService {

    public static final String USER = "USER";
    public static final String ROLE_USER = "ROLE_" + USER;

    private final SecurityRepository securityRepository;

    public JwtUserDetailsService(final SecurityRepository securityRepository) {
        this.securityRepository = securityRepository;
    }

    @Override
    public UserDetails loadUserByUsername(final String username) {
        final Security security = securityRepository.findByLoginIgnoreCase(username);
        if (security == null) {
            log.warn("user not found: {}", username);
            throw new UsernameNotFoundException("User " + username + " not found");
        }
        return new User(username, security.getPassword(), Collections.singletonList(new SimpleGrantedAuthority(ROLE_USER)));
    }

}
