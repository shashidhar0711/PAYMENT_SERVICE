package com.mslearning.PAYMENT_SERVICE.Dto;

import lombok.Data;

@Data
public class TicketDetailsDto {
    private Long ticketId;
    private Long totalAmount;
    private String ticketStatus;
}