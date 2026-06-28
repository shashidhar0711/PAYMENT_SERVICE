package com.mslearning.PAYMENT_SERVICE.Exception;

public class InvalidWebhookSignatureException extends  Exception{
    public InvalidWebhookSignatureException(String message) {
        super(message);
    }
}
