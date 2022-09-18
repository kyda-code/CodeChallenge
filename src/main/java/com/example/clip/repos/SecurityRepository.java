package com.example.clip.repos;

import com.example.clip.domain.Security;
import org.springframework.data.jpa.repository.JpaRepository;


public interface SecurityRepository extends JpaRepository<Security, Long> {

    Security findByLoginIgnoreCase(String login);

    boolean existsByLoginIgnoreCase(String login);

}
