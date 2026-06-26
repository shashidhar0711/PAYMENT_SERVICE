package com.mslearning.PAYMENT_SERVICE.PaymentGateway;

import com.razorpay.PaymentLink;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;
import org.json.JSONObject;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Component
public class RazorPayPaymentService implements PaymentGateway{

    private RazorpayClient razorpayClient;

    public RazorPayPaymentService(RazorpayClient razorpayClient) {
        this.razorpayClient = razorpayClient;
    }

    @Override
    public PaymentLink generatePaymentLink(Long ticketId, Long amount) throws RazorpayException {

        // ORDER DETAILS
        JSONObject paymentLinkRequest = new JSONObject();
        // as of now.. I will send the payment via postman
        paymentLinkRequest.put("amount", amount); // 1000 - > 10.00
        paymentLinkRequest.put("currency","INR");
//        paymentLinkRequest.put("accept_partial",true);
//        paymentLinkRequest.put("first_min_partial_amount",100);
//        paymentLinkRequest.put("expire_by", 1782392100);
        long expireBy = Instant.now().plusSeconds(30 * 60).getEpochSecond();

        System.out.println("Current Epoch : " + Instant.now().getEpochSecond());
        System.out.println("Expire Epoch  : " + expireBy);

        paymentLinkRequest.put("expire_by", expireBy);
        paymentLinkRequest.put("reference_id", ticketId.toString());
        paymentLinkRequest.put("description","Payment for orderId: " + ticketId.toString());


        // CUSTOMER DETAILS
        JSONObject customer = new JSONObject();
        customer.put("name","+918073112156");
        customer.put("contact","Shashi Dhar");
        customer.put("email","shashi.dhar@gmail.com");
        paymentLinkRequest.put("customer",customer);

        // NOTIFY DETAILS
        JSONObject notify = new JSONObject();
        notify.put("sms",true);
        notify.put("email",true);
        paymentLinkRequest.put("notify",notify);

        // PAYMENT DETAILS
        JSONObject notes = new JSONObject();
        paymentLinkRequest.put("notes",notes);
        paymentLinkRequest.put("callback_url","https://www.scaler.com/");
        paymentLinkRequest.put("callback_method","get");

        PaymentLink paymentLink = this.razorpayClient.paymentLink.create(paymentLinkRequest);
        return paymentLink;
    }
}
