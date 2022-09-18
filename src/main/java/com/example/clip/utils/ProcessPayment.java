package com.example.clip.utils;

import com.example.clip.enums.StatusEnum;

import java.math.BigDecimal;

public class ProcessPayment {

    public static BigDecimal calculateFee(BigDecimal amount) {
        if (amount.longValueExact() < 0) {
            return BigDecimal.valueOf(0);
        }

        double percentageFee = 3.5;
        BigDecimal subtractFee = BigDecimal.valueOf(percentageFee / 100);
        subtractFee = subtractFee.multiply(amount);

        amount = amount.subtract(subtractFee);

        return amount;
    }

    public static int getTypeStatus(StatusEnum status){
        switch (status){
            case NEW: return 0;
            case PROCESSED: return 1;
        }
        return -1;
    }
}
