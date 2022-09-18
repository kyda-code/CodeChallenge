package com.example.clip.service;

import com.example.clip.domain.Payment;
import com.example.clip.model.PaymentDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;


@Mapper(
        componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface PaymentMapper {

    PaymentDTO updatePaymentDTO(Payment payment, @MappingTarget PaymentDTO paymentDTO);

    @Mapping(target = "id", ignore = true)
    Payment updatePayment(PaymentDTO paymentDTO, @MappingTarget Payment payment);

}
