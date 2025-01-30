package com.compass.msticketmanager.services;

import com.compass.msticketmanager.dto.TicketDto;
import com.compass.msticketmanager.dto.TicketDtoResponse;
import com.compass.msticketmanager.infra.mqueue.TicketEmissionPublisher;
import com.compass.msticketmanager.model.Event;
import com.compass.msticketmanager.model.Ticket;
import com.compass.msticketmanager.repositories.TicketClient;
import com.compass.msticketmanager.repositories.TicketRepository;
import com.compass.msticketmanager.services.exception.ObjectNotFoundException;
import com.fasterxml.jackson.core.JsonProcessingException;
import feign.FeignException;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TicketService {

    @Autowired
    private TicketRepository ticketRepository;
    @Autowired
    private TicketClient ticketClient;

    private final TicketEmissionPublisher ticketEmissionPublisher;


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

    public List<Ticket> findByEvent(String eventId) {
        Event eventDetails;

        if (eventId != null && !eventId.isEmpty()) {
            try {
                eventDetails = ticketClient.getEventById(eventId);

                if (eventDetails == null || eventDetails.getEventName() == null) {
                    throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Event ID not found");
                }
            } catch (FeignException.NotFound ex) {
                throw new FeignException.NotFound(
                        "Event ID not found: " + eventId, ex.request(), ex.content(), ex.responseHeaders()
                );
            }
        } else {
            throw new IllegalArgumentException("Event ID must be provided");
        }


        return ticketRepository.findByEventId(eventId);
    }

    public Ticket insert(Ticket ticket) {
        Event eventDetails;
        if (ticket.getEventId() != null && !ticket.getEventId().isEmpty()) {
            try {
                eventDetails = ticketClient.getEventById(ticket.getEventId());
                if (eventDetails == null || eventDetails.getEventName() == null) {
                    throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Event ID not found");
                }
                ticket.setEventName(eventDetails.getEventName());
            } catch (FeignException.NotFound ex) {
                throw new FeignException.NotFound(
                        "Event ID not found: " + ticket.getEventId(), ex.request(), ex.content(), ex.responseHeaders()
                );
            }
        } else if (ticket.getEventName() != null && !ticket.getEventName().isEmpty()) {
            throw new IllegalArgumentException("Name search not working");
        } else {
            throw new IllegalArgumentException("Event ID or Event Name must be provided");
        }

        ticket.setEventDetails(eventDetails);
        ticket.setStatus("Active");

        TicketDto ticketDto = new TicketDto(ticket);

        try {
            ticketEmissionPublisher.publish(ticketDto);
            System.out.println("Ticket enviado para a fila com sucesso!");
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Erro ao converter ticket para JSON", e);
        }
        return ticketRepository.insert(ticket);
    }

    public void delete(String id) {
        Ticket ticket = findById(id);
        ticket.setStatus("Canceled");
        ticketRepository.save(ticket);
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
                            objDTO.getCustomerMail(), objDTO.getEventId(), objDTO.getEventName(),
                            objDTO.getBRLamount(), objDTO.getUSDamount(), objDTO.getStatus(), objDTO.getEventDetails());
    }

    public void updateData(Ticket newobj, Ticket obj){
        newobj.setCustomerName(obj.getCustomerName());
        newobj.setCpf(obj.getCpf());
        newobj.setCustomerMail(obj.getCustomerMail());
        newobj.setBRLamount(obj.getBRLamount());
        newobj.setUSDamount(obj.getUSDamount());

    }
}
