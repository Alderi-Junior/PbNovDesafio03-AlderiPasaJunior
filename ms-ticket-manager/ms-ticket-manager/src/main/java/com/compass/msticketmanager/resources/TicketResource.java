package com.compass.msticketmanager.resources;

import com.compass.msticketmanager.dto.TicketDto;
import com.compass.msticketmanager.model.Ticket;
import com.compass.msticketmanager.services.TicketService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value="/tickets")
public class TicketResource {

    @Autowired
    private TicketService ticketService;

    @GetMapping(value = "/get-all-tickets")
    public ResponseEntity<List<TicketDto>> findAll() {
        List<Ticket> tickets = ticketService.findAll();
        List<TicketDto> ticketDtos = tickets.stream().map(t -> new TicketDto(t))
                .collect(Collectors.toList());

        return ResponseEntity.ok().body(ticketDtos);
    }

}
