package com.example.clip.controller;

import static com.example.clip.service.JwtUserDetailsService.ROLE_USER;

import com.example.clip.enums.StatusEnum;
import com.example.clip.request.PaymentRequest;
import com.example.clip.response.DisbursementResponse;
import com.example.clip.model.PaymentDTO;
import com.example.clip.response.ReportPerUserResponse;
import com.example.clip.service.PaymentService;
import com.example.clip.utils.ProcessPayment;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import java.util.List;
import javax.persistence.PersistenceException;
import javax.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping(value = "/api/clip", produces = MediaType.APPLICATION_JSON_VALUE)
@PreAuthorize("hasRole('" + ROLE_USER + "')")
@SecurityRequirement(name = "bearer-jwt")
public class PaymentResource {

    private final PaymentService paymentService;

    public PaymentResource(final PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @GetMapping("/getReportPerUser/{userId}")
    public ReportPerUserResponse getReportPerUser(@PathVariable final String userId){
        return paymentService.reportPerUserResponse(userId);
    }

    @PutMapping("/disbursementProcess")
    public List<DisbursementResponse> disbursementProcess() {
        return paymentService.disbursementProcess();
    }

    @GetMapping
    public ResponseEntity<List<PaymentDTO>> getAllPayments() {
        return ResponseEntity.ok(paymentService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<PaymentDTO> getPayment(@PathVariable final Long id) {
        return ResponseEntity.ok(paymentService.get(id));
    }

    @PostMapping
    @ApiResponse(responseCode = "201")
    public ResponseEntity<Long> createPayment(@RequestBody @Valid final PaymentRequest paymentRequest) {

        PaymentDTO paymentDTO = new PaymentDTO();
        paymentDTO.setAmount(paymentRequest.getAmount());
        paymentDTO.setUserId(paymentRequest.getUserId());
        paymentDTO.setStatusId(ProcessPayment.getTypeStatus(StatusEnum.NEW));

        return new ResponseEntity<>(paymentService.create(paymentDTO), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updatePayment(@PathVariable final Long id,
            @RequestBody @Valid final PaymentRequest paymentRequest) {

        PaymentDTO paymentDTO = new PaymentDTO();
        paymentDTO.setAmount(paymentRequest.getAmount());
        paymentDTO.setUserId(paymentRequest.getUserId());

        paymentService.update(id, paymentDTO);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    @ApiResponse(responseCode = "204")
    public ResponseEntity<Void> deletePayment(@PathVariable final Long id) {
        paymentService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
