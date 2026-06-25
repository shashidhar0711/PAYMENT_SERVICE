package com.mslearning.PAYMENT_SERVICE.Config;

import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RazorPayConfig {
    @Value("${razorpay.key.id}")
    private String key;

    @Value("${razorpay.key.secret}")
    private String secret;

    @Bean
    public RazorpayClient getRazorPayClient() throws RazorpayException {
        return new RazorpayClient(key, secret);
    }

}
