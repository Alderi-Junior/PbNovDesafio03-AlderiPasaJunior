package com.compass.mseventmanager.repositories;

import com.compass.mseventmanager.model.Ticket;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "ticket-service", url = "http://localhost:8091/tickets/")
public interface TicketClient {
    @GetMapping("/check-tickets-by-event/{eventId}")
    List<Ticket> findTicketsByEvent(@PathVariable("eventId") String eventId);

    @DeleteMapping("/delete-ticket-with-event/{id}")
    void deleteTicket(@PathVariable("id") String ticketId);
}
