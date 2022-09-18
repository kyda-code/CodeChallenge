package com.example.clip.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.example.clip.config.BaseIT;
import java.math.BigDecimal;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;


public class PaymentResourceTest extends BaseIT {

    @Test
    @Sql("/data/paymentData.sql")
    public void getAllPayments_success() throws Exception {
        mockMvc.perform(get("/api/payments")
                        .header(HttpHeaders.AUTHORIZATION, bearerToken())
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(((long)1000)));
    }

    @Test
    public void getAllPayments_unauthorized() throws Exception {
        mockMvc.perform(get("/api/payments")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnauthorized())
                .andExpect(jsonPath("$.exception").value("AccessDeniedException"));
    }

    @Test
    @Sql("/data/paymentData.sql")
    public void getPayment_success() throws Exception {
        mockMvc.perform(get("/api/payments/1000")
                        .header(HttpHeaders.AUTHORIZATION, bearerToken())
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.amount").value(95.08));
    }

    @Test
    public void getPayment_notFound() throws Exception {
        mockMvc.perform(get("/api/payments/1666")
                        .header(HttpHeaders.AUTHORIZATION, bearerToken())
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.exception").value("ResponseStatusException"));
    }

    @Test
    public void createPayment_success() throws Exception {
        mockMvc.perform(post("/api/payments")
                        .header(HttpHeaders.AUTHORIZATION, bearerToken())
                        .accept(MediaType.APPLICATION_JSON)
                        .content(readResource("/requests/paymentDTORequest.json"))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());
        assertEquals(1, paymentRepository.count());
    }

    @Test
    public void createPayment_missingField() throws Exception {
        mockMvc.perform(post("/api/payments")
                        .header(HttpHeaders.AUTHORIZATION, bearerToken())
                        .accept(MediaType.APPLICATION_JSON)
                        .content(readResource("/requests/paymentDTORequest_missingField.json"))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.exception").value("MethodArgumentNotValidException"))
                .andExpect(jsonPath("$.fieldErrors[0].field").value("amount"));
    }

    @Test
    @Sql("/data/paymentData.sql")
    public void updatePayment_success() throws Exception {
        mockMvc.perform(put("/api/payments/1000")
                        .header(HttpHeaders.AUTHORIZATION, bearerToken())
                        .accept(MediaType.APPLICATION_JSON)
                        .content(readResource("/requests/paymentDTORequest.json"))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        assertEquals(new BigDecimal("94.08"), paymentRepository.findById(((long)1000)).get().getAmount());
        assertEquals(1, paymentRepository.count());
    }

    @Test
    @Sql("/data/paymentData.sql")
    public void deletePayment_success() throws Exception {
        mockMvc.perform(delete("/api/payments/1000")
                        .header(HttpHeaders.AUTHORIZATION, bearerToken())
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
        assertEquals(0, paymentRepository.count());
    }

}
