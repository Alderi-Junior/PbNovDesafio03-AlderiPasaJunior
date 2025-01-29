package com.compass.mseventmanager.resources;

import com.compass.mseventmanager.dto.EventDTO;
import com.compass.mseventmanager.model.Event;
import com.compass.mseventmanager.services.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;


import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;


@RestController
@RequestMapping(value = "/events")
public class EventResource {

    @Autowired
    private EventService eventService;

    @GetMapping(value = "/get-all-events")
    public ResponseEntity<List<EventDTO>> findAll() {
        List<Event> events = eventService.findAll();
        List<EventDTO> listDTo = events.stream().map(x -> new EventDTO(x))
                .collect(Collectors.toList());
        return ResponseEntity.ok().body(listDTo);
    }

    @GetMapping(value = "/get-event/{id}")
    public ResponseEntity<EventDTO> findById(@PathVariable String id) {
        Event event = eventService.findById(id);
        return ResponseEntity.ok().body(new EventDTO(event));
    }

    @GetMapping(value = "/get-all-events-sorted")
    public ResponseEntity<List<EventDTO>> findAllSorted() {
        List<Event> events = eventService.findAllWSort();
        List<EventDTO> listDTo = events.stream().map(x -> new EventDTO(x))
                .collect(Collectors.toList());
        return ResponseEntity.ok().body(listDTo);
    }

    @PostMapping(value = "/post-new-event")
    public ResponseEntity<Void> insert(@RequestBody EventDTO objDTO) {
        Event obj = eventService.fromDTO(objDTO);
        obj = eventService.insert(obj);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(obj.getId()).toUri();
        return ResponseEntity.created(uri).build();
    }

    @DeleteMapping(value = "/delete-event/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        eventService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping(value = "/update-event/{id}")
    public ResponseEntity<Void> update(@RequestBody EventDTO objDTO, @PathVariable String id) {
        Event obj = eventService.fromDTO(objDTO);
        obj.setId(id);
        obj = eventService.update(obj);
        return ResponseEntity.noContent().build();
    }

}

