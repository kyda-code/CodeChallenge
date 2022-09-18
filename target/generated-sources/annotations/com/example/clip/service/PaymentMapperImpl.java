package com.example.clip.service;

import com.example.clip.domain.Payment;
import com.example.clip.model.PaymentDTO;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2022-09-17T13:35:52-0500",
    comments = "version: 1.5.2.Final, compiler: javac, environment: Java 18.0.2.1 (Oracle Corporation)"
)
@Component
public class PaymentMapperImpl implements PaymentMapper {

    @Override
    public PaymentDTO updatePaymentDTO(Payment payment, PaymentDTO paymentDTO) {
        if ( payment == null ) {
            return paymentDTO;
        }

        paymentDTO.setId( payment.getId() );
        paymentDTO.setAmount( payment.getAmount() );
        paymentDTO.setUserId( payment.getUserId() );
        paymentDTO.setStatusId( payment.getStatusId() );

        return paymentDTO;
    }

    @Override
    public Payment updatePayment(PaymentDTO paymentDTO, Payment payment) {
        if ( paymentDTO == null ) {
            return payment;
        }

        payment.setAmount( paymentDTO.getAmount() );
        payment.setUserId( paymentDTO.getUserId() );
        payment.setStatusId( paymentDTO.getStatusId() );

        return payment;
    }
}
