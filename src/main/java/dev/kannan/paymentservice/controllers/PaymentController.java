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

    public PaymentController(@Qualifier("stripePaymentGateway") PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @PostMapping()
    public String generatePaymentLink(@RequestBody GeneratePaymentLinkRequestDto generatePaymentLinkRequestDto) throws Exception {
        return paymentService.generatePaymentLink(generatePaymentLinkRequestDto.getOrderId());
    }

    @PostMapping("/webhook")
    public void handleWebHook(@RequestBody Object webHook) {
        //the logic for webhook implementation goes here
        //Object -> temporarily marking it as object. will create a proper DTO at the time of implementation
    }
}
