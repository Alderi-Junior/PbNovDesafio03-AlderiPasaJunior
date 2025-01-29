package com.compass.msticketmanager.services;

import com.compass.msticketmanager.dto.TicketDto;
import com.compass.msticketmanager.model.Event;
import com.compass.msticketmanager.model.Ticket;
import com.compass.msticketmanager.repositories.TicketClient;
import com.compass.msticketmanager.repositories.TicketRepository;
import com.compass.msticketmanager.services.exception.ObjectNotFoundException;
import feign.FeignException;
import feign.Request;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Optional;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TicketServiceTest {

    @Mock
    private TicketRepository ticketRepository;

    @Mock
    private TicketClient ticketClient;

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

    @Test
    void testFindById() {
        when(ticketRepository.findById(anyString())).thenReturn(Optional.of(ticket));

        Ticket foundTicket = ticketService.findById("1");

        assertNotNull(foundTicket);
        assertEquals(ticket, foundTicket);
    }

    @Test
    void testFindById_NotFound() {
        when(ticketRepository.findById(anyString())).thenReturn(Optional.empty());

        assertThrows(ObjectNotFoundException.class, () -> ticketService.findById("2"));
    }

    @Test
    void testFindByCpf() {
        when(ticketRepository.findByCpf(anyString())).thenReturn(Arrays.asList(ticket));

        var tickets = ticketService.findByCpf("12345678900");

        assertEquals(1, tickets.size());
        assertEquals(ticket, tickets.get(0));
    }

    @Test
    void testInsert() {
        when(ticketClient.getEventById(anyString())).thenReturn(event);
        when(ticketRepository.insert(any(Ticket.class))).thenReturn(ticket);

        Ticket insertedTicket = ticketService.insert(ticket);

        assertNotNull(insertedTicket);
        assertEquals(ticket, insertedTicket);
    }

    @Test
    void testInsert_EventNotFound() {
        Request request = Request.create(Request.HttpMethod.GET, "/events/1", new HashMap<>(), null, null, null);

        when(ticketClient.getEventById(anyString())).thenThrow(new FeignException.NotFound("Event ID not found", request, null, null));

        assertThrows(FeignException.NotFound.class, () -> ticketService.insert(ticket));
    }

}