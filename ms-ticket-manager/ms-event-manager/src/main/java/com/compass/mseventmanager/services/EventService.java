package com.compass.mseventmanager.services;


import com.compass.mseventmanager.dto.EventDTO;
import com.compass.mseventmanager.model.Event;
import com.compass.mseventmanager.repositories.AddressFeign;
import com.compass.mseventmanager.repositories.EventRepository;
import com.compass.mseventmanager.services.exception.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class EventService {

    @Autowired
    private EventRepository eventRepository;
    @Autowired
    private AddressFeign addressFeign;

    public List<Event> findAll() {
        return eventRepository.findAll();
    }

    public Event findById(String id){
        Optional<Event> event = eventRepository.findById(id);
        return event.orElseThrow(() -> new ObjectNotFoundException("Objeto não encontrado"));
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
        findById(id);
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
            throw new ObjectNotFoundException("Objeto não encontrado");
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
