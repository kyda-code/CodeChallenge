package com.example.clip.service;

import com.example.clip.domain.Payment;
import com.example.clip.enums.StatusEnum;
import com.example.clip.response.DisbursementResponse;
import com.example.clip.model.PaymentDTO;
import com.example.clip.response.ReportPerUserResponse;
import com.example.clip.repos.PaymentRepository;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.example.clip.utils.ProcessPayment;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;


@Service
public class PaymentServiceImpl implements PaymentService {

    private final PaymentRepository paymentRepository;
    private final PaymentMapper paymentMapper;

    public PaymentServiceImpl(final PaymentRepository paymentRepository,
                              final PaymentMapper paymentMapper) {
        this.paymentRepository = paymentRepository;
        this.paymentMapper = paymentMapper;
    }

    @Override
    public List<PaymentDTO> findAll() {
        return paymentRepository.findAll(Sort.by("id"))
                .stream()
                .map(payment -> paymentMapper.updatePaymentDTO(payment, new PaymentDTO()))
                .collect(Collectors.toList());
    }

    @Override
    public PaymentDTO get(final Long id) {
        return paymentRepository.findById(id)
                .map(payment -> paymentMapper.updatePaymentDTO(payment, new PaymentDTO()))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @Override
    public Long create(final PaymentDTO paymentDTO) {
        final Payment payment = new Payment();
        paymentMapper.updatePayment(paymentDTO, payment);
        return paymentRepository.save(payment).getId();
    }

    @Override
    public void update(final Long id, final PaymentDTO paymentDTO) {
        final Payment payment = paymentRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        paymentMapper.updatePayment(paymentDTO, payment);
        paymentRepository.save(payment);
    }

    @Override
    public void delete(final Long id) {
        paymentRepository.deleteById(id);
    }

    @Override
    public ReportPerUserResponse reportPerUserResponse(String userId) {
        List<Payment> payments = new ArrayList<>();
        ReportPerUserResponse reportPerUserResponse = new ReportPerUserResponse();

        payments = paymentRepository.findPaymentsByUserId(userId);

        reportPerUserResponse.setUserId(userId);
        reportPerUserResponse.setTotalPaymentsSum(payments.size());
        reportPerUserResponse.setNewPaymentsSum(payments.stream()
                .filter(payment -> payment.getStatusId() == ProcessPayment.getTypeStatus(StatusEnum.NEW))
                .count());

        BigDecimal amount = BigDecimal.ZERO;
        amount = payments.stream()
                .filter(payment -> payment.getStatusId() == ProcessPayment.getTypeStatus(StatusEnum.NEW))
                .map(Payment::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        reportPerUserResponse.setNewPaymentsAmount(amount);

        return reportPerUserResponse;
    }

    @Override
    public List<DisbursementResponse> disbursementProcess() {
        List<Payment> payments = new ArrayList<>();
        List<DisbursementResponse> responseList = new ArrayList<>();

        paymentRepository.findPaymentsByStatusId(ProcessPayment.getTypeStatus(StatusEnum.NEW)).forEach(payment -> payments.add(payment));

        for (Payment payment : payments) {
            DisbursementResponse response = new DisbursementResponse();
            response.setUserId(payment.getUserId());
            response.setPayment(payment.getAmount());

            payment.setAmount(ProcessPayment.calculateFee(payment.getAmount()));
            response.setDisbursement(String.format("%s %s %.2f", payment.getUserId(), ":", payment.getAmount()));

            payment.setStatusId(ProcessPayment.getTypeStatus(StatusEnum.PROCESSED));

            responseList.add(response);
        }

        //Create a table of original
        paymentRepository.saveAllAndFlush(payments);

        return responseList;    }

}
