package dev.kannan.paymentservice.controllers;

import com.razorpay.RazorpayException;
import dev.kannan.paymentservice.dtos.GeneratePaymentLinkRequestDto;
import dev.kannan.paymentservice.services.PaymentService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/payments")
public class PaymentController {
    private PaymentService paymentService;

    public PaymentController(@Qualifier("razorpayPaymentGateway") PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @PostMapping()
    public String generatePaymentLink(@RequestBody GeneratePaymentLinkRequestDto generatePaymentLinkRequestDto) throws RazorpayException {
        return paymentService.generatePaymentLink(generatePaymentLinkRequestDto.getOrderId());
    }
}
