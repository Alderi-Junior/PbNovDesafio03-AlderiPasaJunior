package com.compass.msticketmanager.dto;

import com.compass.msticketmanager.model.Event;
import com.compass.msticketmanager.model.Ticket;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TicketDto implements Serializable {
    private static final long serialVersionUID = 1L;

    private String ticketId;
    private String customerName;
    private String cpf;
    private String customerMail;
    private String eventId;
    private String eventName;
    private BigDecimal BRLamount;
    private BigDecimal USDamount;
    private String status;
    private Event eventDetails;

    public TicketDto(Ticket ticket) {
        this.ticketId = ticket.getTicketId();
        this.customerName = ticket.getCustomerName();
        this.cpf = ticket.getCpf();
        this.customerMail = ticket.getCustomerMail();
        this.eventId = ticket.getEventId();
        this.eventName = ticket.getEventName();
        this.BRLamount = ticket.getBRLamount();
        this.USDamount = ticket.getUSDamount();
        this.status = ticket.getStatus();
        this.eventDetails = ticket.getEventDetails();
    }

    public TicketDto(Ticket ticket, Event event) {
        this.ticketId = ticket.getTicketId();
        this.customerName = ticket.getCustomerName();
        this.cpf = ticket.getCpf();
        this.customerMail = ticket.getCustomerMail();
        this.BRLamount = ticket.getBRLamount();
        this.USDamount = ticket.getUSDamount();
        this.status = ticket.getStatus();
        this.eventDetails = event;

    }


}
