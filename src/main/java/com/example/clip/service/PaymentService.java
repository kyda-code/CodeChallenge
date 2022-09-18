package com.example.clip.service;

import com.example.clip.response.DisbursementResponse;
import com.example.clip.model.PaymentDTO;
import com.example.clip.response.ReportPerUserResponse;

import java.util.List;


public interface PaymentService {

    List<PaymentDTO> findAll();

    PaymentDTO get(final Long id);

    Long create(final PaymentDTO paymentDTO);

    void update(final Long id, final PaymentDTO paymentDTO);

    void delete(final Long id);

    ReportPerUserResponse reportPerUserResponse(String userId);

    List<DisbursementResponse> disbursementProcess();
}
