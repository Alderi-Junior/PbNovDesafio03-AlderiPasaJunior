package com.compass.msticketmanager.services;

import com.compass.msticketmanager.dto.TicketDto;
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

    public Ticket insert(Ticket ticket) {
        return ticketRepository.insert(ticket);
    }

    public void delete(String id) {
        findById(id);
        ticketRepository.deleteById(id);
    }

    public Ticket fromDTO(TicketDto objDTO){
        return new Ticket(objDTO.getTicketId(), objDTO.getCustomerName(), objDTO.getCpf(),
                            objDTO.getCustomerMail(),objDTO.getEventId(), objDTO.getEventName(),
                            objDTO.getBRLamount(), objDTO.getUSDamount());
    }
}
