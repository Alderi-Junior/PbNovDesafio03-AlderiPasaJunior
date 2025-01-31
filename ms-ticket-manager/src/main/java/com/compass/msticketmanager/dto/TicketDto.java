package com.compass.msticketmanager.dto;

import com.compass.msticketmanager.model.Event;
import com.compass.msticketmanager.model.Ticket;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class TicketDto implements Serializable {
    private static final long serialVersionUID = 1L;

    private String ticketId;
    private String customerName;
    private String cpf;
    private String customerMail;
    private BigDecimal brlAmount;
    private BigDecimal usdAmount;
    private String status;
    private String eventId;
    private String eventName;
    private Event eventDetails;

    public TicketDto(Ticket ticket) {
        this.ticketId = ticket.getTicketId();
        this.customerName = ticket.getCustomerName();
        this.cpf = ticket.getCpf();
        this.customerMail = ticket.getCustomerMail();
        this.brlAmount = ticket.getBrlAmount();
        this.usdAmount = ticket.getUsdAmount();
        this.status = ticket.getStatus();
        this.eventId = ticket.getEventId();
        this.eventName = ticket.getEventName();
        this.eventDetails = ticket.getEventDetails();
    }

    public TicketDto(Ticket ticket, Event event) {
        this.ticketId = ticket.getTicketId();
        this.customerName = ticket.getCustomerName();
        this.cpf = ticket.getCpf();
        this.customerMail = ticket.getCustomerMail();
        this.brlAmount = ticket.getBrlAmount();
        this.usdAmount = ticket.getUsdAmount();
        this.status = ticket.getStatus();
        this.eventDetails = event;

    }


}
