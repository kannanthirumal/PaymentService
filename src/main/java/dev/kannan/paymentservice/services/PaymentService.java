package dev.kannan.paymentservice.services;

import com.razorpay.RazorpayException;
import dev.kannan.paymentservice.dtos.GeneratePaymentLinkRequestDto;
import org.springframework.web.bind.annotation.RequestBody;

public interface PaymentService {
    String generatePaymentLink(Long orderId) throws RazorpayException;
}
