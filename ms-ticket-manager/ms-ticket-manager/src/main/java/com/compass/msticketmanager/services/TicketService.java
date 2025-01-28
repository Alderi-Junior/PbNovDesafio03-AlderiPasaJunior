package com.compass.msticketmanager.services;

import com.compass.msticketmanager.dto.TicketDto;
import com.compass.msticketmanager.model.Ticket;
import com.compass.msticketmanager.repositories.TicketRepository;
import com.compass.msticketmanager.services.exception.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
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
        Optional<Ticket> ticket = ticketRepository.findById(id);
        return ticket.orElseThrow(() -> new ObjectNotFoundException("Object Not Found"));
    }

    public List<Ticket> findByCpf(String cpf) {
        return ticketRepository.findByCpf(cpf);
    }

    public Ticket insert(Ticket ticket) {
        return ticketRepository.insert(ticket);
    }

    public void delete(String id) {
        findById(id);
        ticketRepository.deleteById(id);
    }

    @Transactional
    public Ticket update(Ticket obj) {
        Optional<Ticket> ticket = ticketRepository.findById(obj.getTicketId());
        if(ticket.isPresent()) {
            Ticket newobj = ticket.get();
            updateData(newobj,obj);
            return ticketRepository.save(newobj);
        }else {
            throw new ObjectNotFoundException("Object Not Found");
        }
    }

    public Ticket fromDTO(TicketDto objDTO){
        return new Ticket(objDTO.getTicketId(), objDTO.getCustomerName(), objDTO.getCpf(),
                            objDTO.getCustomerMail(),objDTO.getEventId(), objDTO.getEventName(),
                            objDTO.getBRLamount(), objDTO.getUSDamount());
    }

    public void updateData(Ticket newobj, Ticket obj){
        newobj.setCustomerName(obj.getCustomerName());
        newobj.setCpf(obj.getCpf());
        newobj.setCustomerMail(obj.getCustomerMail());
        newobj.setBRLamount(obj.getBRLamount());
        newobj.setUSDamount(obj.getUSDamount());

    }
}
