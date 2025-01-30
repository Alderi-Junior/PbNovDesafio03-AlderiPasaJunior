package com.compass.msticketmanager.dto;

import com.compass.msticketmanager.model.Event;
import com.compass.msticketmanager.model.Ticket;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
@Data
@NoArgsConstructor
public class TicketDtoResponse {

    private String ticketId;
    private String customerName;
    private String cpf;
    private String customerMail;
    private BigDecimal brlAmount;
    private BigDecimal usdAmount;
    private String status;
    private Event eventDetails;

    public TicketDtoResponse(Ticket ticket) {
        this.ticketId = ticket.getTicketId();
        this.customerName = ticket.getCustomerName();
        this.cpf = ticket.getCpf();
        this.customerMail = ticket.getCustomerMail();
        this.brlAmount = ticket.getBrlAmount();
        this.usdAmount = ticket.getUsdAmount();
        this.status = ticket.getStatus();
        this.eventDetails = ticket.getEventDetails();
    }
}
