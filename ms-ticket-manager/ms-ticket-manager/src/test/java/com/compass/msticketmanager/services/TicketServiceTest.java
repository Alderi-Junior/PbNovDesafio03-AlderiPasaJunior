package com.compass.msticketmanager.services;

import com.compass.msticketmanager.dto.TicketDto;
import com.compass.msticketmanager.model.Event;
import com.compass.msticketmanager.model.Ticket;
import com.compass.msticketmanager.repositories.TicketRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TicketServiceTest {

    @Mock
    private TicketRepository ticketRepository;


    @InjectMocks
    private TicketService ticketService;

    private Ticket ticket;
    private TicketDto ticketDto;
    private Event event;

    @BeforeEach
    void setUp() {
        event = new Event("1", "Event Name", null, "", "", "", "", "");
        ticket = new Ticket("1", "Customer Name", "12345678900", "customer@mail.com", "1", "Event Name", BigDecimal.valueOf(100.00), BigDecimal.valueOf(50.00), "Active", event);
        ticketDto = new TicketDto("1", "Customer Name", "12345678900", "customer@mail.com", "1", "Event Name", BigDecimal.valueOf(100.00), BigDecimal.valueOf(50.00), "Active", event);
    }

    @Test
    void testFindAll() {
        when(ticketRepository.findAll()).thenReturn(Arrays.asList(ticket));

        var tickets = ticketService.findAll();

        assertEquals(1, tickets.size());
        assertEquals(ticket, tickets.get(0));
    }
}