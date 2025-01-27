package com.compass.msticketmanager.dto;

import com.compass.msticketmanager.model.Ticket;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
@NoArgsConstructor
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

    public TicketDto(Ticket ticket) {
        this.ticketId = ticket.getTicketId();
        this.customerName = ticket.getCustomerName();
        this.cpf = ticket.getCpf();
        this.customerMail = ticket.getCustomerMail();
        this.eventId = ticket.getEventId();
        this.eventName = ticket.getEventName();
        this.BRLamount = ticket.getBRLamount();
        this.USDamount = ticket.getUSDamount();

    }
}
