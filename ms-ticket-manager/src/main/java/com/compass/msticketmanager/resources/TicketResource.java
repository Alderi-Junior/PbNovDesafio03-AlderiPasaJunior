package com.compass.msticketmanager.resources;

import com.compass.msticketmanager.dto.TicketDto;
import com.compass.msticketmanager.dto.TicketDtoResponse;
import com.compass.msticketmanager.model.Ticket;
import com.compass.msticketmanager.services.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value="/tickets")
public class TicketResource {

    @Autowired
    private TicketService ticketService;

    @GetMapping(value = "/get-all-tickets")
    public ResponseEntity<List<TicketDtoResponse>> findAll() {
        List<Ticket> tickets = ticketService.findAll();
        List<TicketDtoResponse> ticketDtos = tickets.stream().map
                        (t -> new TicketDtoResponse(t))
                       .collect(Collectors.toList());

        return ResponseEntity.ok().body(ticketDtos);
    }

    @GetMapping(value = "/get-ticket/{id}")
    public ResponseEntity<TicketDtoResponse> findById(@PathVariable String id) {
        Ticket ticket = ticketService.findById(id);
        return ResponseEntity.ok().body(new TicketDtoResponse(ticket));
    }

    @GetMapping(value = "/get-ticket-by-cpf/{cpf}")
    public ResponseEntity<List<TicketDtoResponse>> findByCpf(@PathVariable String cpf) {
        List<Ticket> tickets = ticketService.findByCpf(cpf);
        List<TicketDtoResponse> ticketDtos = tickets.stream()
                .map(TicketDtoResponse::new)
                .collect(Collectors.toList());
        return ResponseEntity.ok().body(ticketDtos);
    }

    @GetMapping(value = "/check-tickets-by-event/{eventId}")
    public ResponseEntity<List<TicketDtoResponse>> findByEvent(@PathVariable String eventId) {
        List<Ticket> tickets = ticketService.findByEvent(eventId);
        List<TicketDtoResponse> ticketDtos = tickets.stream()
                .map(TicketDtoResponse::new)
                .collect(Collectors.toList());
        return ResponseEntity.ok().body(ticketDtos);
    }

    @PostMapping(value = "post-new-ticket")
    public ResponseEntity<Void> insert(@RequestBody TicketDto ticketDto) {
        Ticket obj = ticketService.fromDTO(ticketDto);
        obj = ticketService.insert(obj);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}").buildAndExpand(obj.getTicketId()).toUri();
        return ResponseEntity.created(uri).build();
    }

    @DeleteMapping(value = "/delete-ticket/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        ticketService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping(value = "/delete-ticket-with-event/{id}")
    public ResponseEntity<Void> deleteWithEvent(@PathVariable String id) {
        ticketService.forceDelete(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping(value = "/update-ticket/{id}")
    public ResponseEntity<Void> update(@RequestBody TicketDto ticketDto, @PathVariable String id) {
        Ticket obj = ticketService.fromDTO(ticketDto);
        obj.setTicketId(id);
        obj = ticketService.update(obj);
        return ResponseEntity.noContent().build();
    }

}
