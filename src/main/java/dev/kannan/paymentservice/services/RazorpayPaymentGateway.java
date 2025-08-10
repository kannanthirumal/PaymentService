package dev.kannan.paymentservice.services;

import com.razorpay.PaymentLink;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

@Service("razorpayPaymentGateway")
public class RazorpayPaymentGateway implements PaymentService {

    private RazorpayClient razorpayClient;
    public RazorpayPaymentGateway(RazorpayClient razorpayClient) {
        this.razorpayClient = razorpayClient;
    }


    @Override
    public String generatePaymentLink(Long orderId) throws RazorpayException {
        JSONObject paymentLinkRequest = new JSONObject();
        paymentLinkRequest.put("amount",1000); //it's 10 rupee, 10 * 100 = 1000 -> to paise
        paymentLinkRequest.put("currency","INR");
        /**
         * we won't accept partial payments
         **/
        //paymentLinkRequest.put("accept_partial",true);
        //paymentLinkRequest.put("first_min_partial_amount",100);

        /**
         * expiration time -> ex: 10 minutes to pay | it's in the epoch format
         * epoch -> number of seconds (or other time units) elapsed since this epoch
         * the most common epoch is the Unix epoch, which starts at 00:00:00 UTC on January 1, 1970
         */
        //paymentLinkRequest.put("expire_by",1691097057);

        /**
         * here - we will take the current system epoch in milliseconds
         * and then add 10 minutes to it
         * milliseconds in 10 minutes -> 10 * 60 * 1000
         * 1 minute has 60 seconds and 1 second has 1000 milliseconds
         */
        paymentLinkRequest.put("expire_by",System.currentTimeMillis() + (10 * 60 * 1000));
        paymentLinkRequest.put("reference_id",orderId.toString());
        paymentLinkRequest.put("description","Payment for policy no #23456");

        /**
         * user details - we will get it from the user service
         */
        JSONObject customer = new JSONObject();
        customer.put("name","+919999999999");
        customer.put("contact","Gaurav Kumar");
        customer.put("email","gaurav.kumar@example.com");
        paymentLinkRequest.put("customer",customer);


        JSONObject notify = new JSONObject();
        notify.put("sms",true);
        notify.put("email",true);
        paymentLinkRequest.put("reminder_enable",true);

        //JSONObject notes = new JSONObject();
        //notes.put("policy_name","Jeevan Bima");
        //paymentLinkRequest.put("notes",notes);

        paymentLinkRequest.put("callback_url","https://google.com/");
        paymentLinkRequest.put("callback_method","get");

        /** I created the instance and replaced it here as per the razorpay github doc **/
        //PaymentLink payment = instance.paymentLink.create(paymentLinkRequest);
        PaymentLink payment = razorpayClient.paymentLink.create(paymentLinkRequest);

        return payment.toString();
    }
}
