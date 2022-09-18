package com.example.clip.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.example.clip.config.BaseIT;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;


public class RegistrationResourceTest extends BaseIT {

    @Test
    public void register_success() throws Exception {
        mockMvc.perform(post("/register")
                        .header(HttpHeaders.AUTHORIZATION, bearerToken())
                        .accept(MediaType.APPLICATION_JSON)
                        .content(readResource("/requests/registrationRequest.json"))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        assertEquals(2, securityRepository.count());
    }

}
