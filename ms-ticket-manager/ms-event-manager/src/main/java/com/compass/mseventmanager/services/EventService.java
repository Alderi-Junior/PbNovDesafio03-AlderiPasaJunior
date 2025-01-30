package com.compass.mseventmanager.services;


import com.compass.mseventmanager.dto.EventDTO;
import com.compass.mseventmanager.model.Event;
import com.compass.mseventmanager.model.Ticket;
import com.compass.mseventmanager.repositories.AddressFeign;
import com.compass.mseventmanager.repositories.EventRepository;
import com.compass.mseventmanager.repositories.TicketClient;
import com.compass.mseventmanager.services.exception.EventWithActiveTicketsException;
import com.compass.mseventmanager.services.exception.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
public class EventService {

    @Autowired
    private EventRepository eventRepository;
    @Autowired
    private AddressFeign addressFeign;
    @Autowired
    private TicketClient ticketClient;

    public List<Event> findAll() {
        return eventRepository.findAll();
    }

    public Event findById(String id){
        Optional<Event> event = eventRepository.findById(id);
        return event.orElseThrow(() -> new ObjectNotFoundException("Object not found!"));
    }

    public Event insert(Event obj){
        var address = addressFeign.getAddress(obj.getCep());

        obj.setLogradouro(address.logradouro());
        obj.setBairro(address.bairro());
        obj.setCidade(address.localidade());
        obj.setUf(address.uf());

        return eventRepository.insert(obj);
    }

    public void delete(String id){
        List<Ticket> tickets = ticketClient.findTicketsByEvent(id);

        boolean hasActiveTickets = tickets.stream().anyMatch(
                ticket -> "Active".equals(ticket.getStatus()));

        if (hasActiveTickets) {
            throw new EventWithActiveTicketsException(
                    "Cannot delete event with active tickets."
            );
        }

        for (Ticket ticket : tickets) {
            ticketClient.deleteTicket(ticket.getTicketId());
        }

        eventRepository.deleteById(id);
    }

    @Transactional
    public Event update(Event obj){
        Optional<Event> event = eventRepository.findById(obj.getId());
        if(event.isPresent()){
            Event newobj = event.get();
            updateData(newobj,obj);
            return eventRepository.save(newobj);
        }else {
            throw new ObjectNotFoundException("Object not found!");
        }
    }

    public List<Event> findAllWSort(){
        return eventRepository.findAllByOrderByEventNameAsc();
    }

    public Event fromDTO(EventDTO objDTO){
        return new Event(objDTO.getId(), objDTO.getEventName(),objDTO.getDateTime(),
                         objDTO.getCep(), objDTO.getLogradouro(), objDTO.getBairro(),
                         objDTO.getCidade(), objDTO.getUf());
    }

    private void updateData(Event newobj, Event obj) {
        newobj.setEventName(obj.getEventName());
        newobj.setDateTime(obj.getDateTime());
        newobj.setCep(obj.getCep());
    }
}
