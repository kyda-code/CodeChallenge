package com.example.clip.service;

import com.example.clip.domain.Security;
import com.example.clip.request.RegistrationRequest;
import com.example.clip.repos.SecurityRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
@Slf4j
public class RegistrationService {

    private final SecurityRepository securityRepository;
    private final PasswordEncoder passwordEncoder;

    public RegistrationService(final SecurityRepository securityRepository,
            final PasswordEncoder passwordEncoder) {
        this.securityRepository = securityRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public boolean loginExists(final RegistrationRequest registrationRequest) {
        return securityRepository.existsByLoginIgnoreCase(registrationRequest.getLogin());
    }

    public void register(final RegistrationRequest registrationRequest) {
        log.info("registering new user: {}", registrationRequest.getLogin());

        final Security security = new Security();
        security.setLogin(registrationRequest.getLogin());
        security.setPassword(passwordEncoder.encode(registrationRequest.getPassword()));
        securityRepository.save(security);
    }

}
