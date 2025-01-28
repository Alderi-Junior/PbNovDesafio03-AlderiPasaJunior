package com.compass.msticketmanager.resources;

import com.compass.msticketmanager.dto.TicketDto;
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
    public ResponseEntity<List<TicketDto>> findAll() {
        List<Ticket> tickets = ticketService.findAll();
        List<TicketDto> ticketDtos = tickets.stream().map(t -> new TicketDto(t))
                .collect(Collectors.toList());

        return ResponseEntity.ok().body(ticketDtos);
    }

    @GetMapping(value = "/get-ticket/{id}")
    public ResponseEntity<TicketDto> findById(@PathVariable String id) {
        Ticket ticket = ticketService.findById(id);
        return ResponseEntity.ok().body(new TicketDto(ticket));
    }

    @GetMapping(value = "/get-ticket-by-cpf/{cpf}")
    public ResponseEntity<List<TicketDto>> findByCpf(@PathVariable String cpf) {
        List<Ticket> tickets = ticketService.findByCpf(cpf);
        List<TicketDto> ticketDtos = tickets.stream()
                .map(TicketDto::new)
                .collect(Collectors.toList());
        return ResponseEntity.ok().body(ticketDtos);
    }

    @PostMapping(value = "post-new-ticket")
    public ResponseEntity<Void> insert(@RequestBody TicketDto ticketDto) {
        Ticket obj = ticketService.fromDTO(ticketDto);
        obj = ticketService.insert(obj);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(obj.getTicketId()).toUri();
        return ResponseEntity.created(uri).build();
    }

    @DeleteMapping(value = "/delete-ticket/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        ticketService.delete(id);
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
