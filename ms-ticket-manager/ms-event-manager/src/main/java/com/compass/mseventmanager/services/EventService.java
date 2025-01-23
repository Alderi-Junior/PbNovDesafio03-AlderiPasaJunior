package com.compass.mseventmanager.services;


import com.compass.mseventmanager.dto.EventDTO;
import com.compass.mseventmanager.model.Event;
import com.compass.mseventmanager.repositories.EventRepository;
import com.compass.mseventmanager.services.exception.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EventService {

    @Autowired
    private EventRepository eventRepository;

    public List<Event> findAll() {
        return eventRepository.findAll();
    }


    public Event findById(String id){
        Optional<Event> event = eventRepository.findById(id);
        return event.orElseThrow(() -> new ObjectNotFoundException("Objeto não encontrado"));
    }

    public Event insert(Event obj){
        return eventRepository.insert(obj);
    }

    public void delete(String id){
        findById(id);
        eventRepository.deleteById(id);
    }

    public Event fromDTO(EventDTO objDTO){
        return new Event(objDTO.getId(), objDTO.getEventName(),objDTO.getLocalDateTime(), objDTO.getCep(),
                objDTO.getLogradouro(), objDTO.getBairro(), objDTO.getCidade(), objDTO.getUf());
    }
}
