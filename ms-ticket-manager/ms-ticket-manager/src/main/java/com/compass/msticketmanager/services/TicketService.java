package com.compass.msticketmanager.services;

import com.compass.msticketmanager.model.Ticket;
import com.compass.msticketmanager.repositories.TicketRepository;
import com.compass.msticketmanager.services.exception.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TicketService {

    @Autowired
    private TicketRepository ticketRepository;

    public List<Ticket> findAll() {
        return ticketRepository.findAll();
    }

    public Ticket findById(String id){
        Optional<Ticket> event = ticketRepository.findById(id);
        return event.orElseThrow(() -> new ObjectNotFoundException("Objeto não encontrado"));
    }
}
