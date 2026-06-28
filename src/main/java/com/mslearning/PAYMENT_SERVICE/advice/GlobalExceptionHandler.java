package com.mslearning.PAYMENT_SERVICE.advice;

import com.mslearning.PAYMENT_SERVICE.Dto.ExceptionResponseDto;
import com.mslearning.PAYMENT_SERVICE.Exception.InvalidWebhookSignatureException;
import com.mslearning.PAYMENT_SERVICE.Exception.PaymentNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(InvalidWebhookSignatureException.class)
    public ResponseEntity<ExceptionResponseDto> handleResourceNotFound(InvalidWebhookSignatureException exception) {
        ExceptionResponseDto exceptionResponseDto = new ExceptionResponseDto();
        exceptionResponseDto.setMessage(exception.getMessage());
        exceptionResponseDto.setStatus(HttpStatus.BAD_REQUEST);

        return new ResponseEntity<>(exceptionResponseDto, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(PaymentNotFoundException.class)
    public ResponseEntity<ExceptionResponseDto> handleResourceNotFound(PaymentNotFoundException exception) {
        ExceptionResponseDto exceptionResponseDto = new ExceptionResponseDto();
        exceptionResponseDto.setMessage(exception.getMessage());
        exceptionResponseDto.setStatus(HttpStatus.NOT_FOUND);

        return new ResponseEntity<>(exceptionResponseDto, HttpStatus.NOT_FOUND);
    }
}
