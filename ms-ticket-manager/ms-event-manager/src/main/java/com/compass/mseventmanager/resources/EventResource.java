package com.compass.mseventmanager.resources;

import com.compass.mseventmanager.dto.EventDTO;
import com.compass.mseventmanager.model.Event;
import com.compass.mseventmanager.services.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import java.util.ArrayList;
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
        List<EventDTO> listDTo = events.stream().map(x -> new EventDTO(x)).collect(Collectors.toList());
        return ResponseEntity.ok().body(listDTo);
    }

    @GetMapping(value = "/get-event/{id}")
    public ResponseEntity<EventDTO> findById(@PathVariable String id) {
        Event event = eventService.findById(id);
        return ResponseEntity.ok().body(new EventDTO(event));
    }
}

