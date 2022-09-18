package com.example.clip.response;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class DisbursementResponse {
    private String userId;
    private BigDecimal payment;
    private String disbursement;
}
