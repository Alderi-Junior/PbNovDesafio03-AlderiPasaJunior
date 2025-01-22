package com.compass.mseventmanager.resources;

import com.compass.mseventmanager.model.Event;
import com.compass.mseventmanager.services.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import java.util.List;


@RestController
@RequestMapping(value = "/events")
public class EventResource {

    @Autowired
    private EventService eventService;

    @GetMapping(value = "/get-all-events")
    public ResponseEntity<List<Event>> findAll() {
        List<Event> events = eventService.findAll();
        return ResponseEntity.ok().body(events);
    }
}
