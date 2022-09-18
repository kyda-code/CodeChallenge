package com.example.clip.request;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;

@Getter
@Setter
public class PaymentRequest {

    @NotNull
    @Size(max = 255)
    private String userId;

    @NotNull
    @Digits(integer = 10, fraction = 2)
    private BigDecimal amount;
}
