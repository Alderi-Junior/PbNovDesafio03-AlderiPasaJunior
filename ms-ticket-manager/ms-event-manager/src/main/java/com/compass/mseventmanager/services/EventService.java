package com.compass.mseventmanager.services;


import com.compass.mseventmanager.model.Event;
import com.compass.mseventmanager.repositories.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EventService {

    @Autowired
    private EventRepository eventRepository;

    public List<Event> findAll() {
        return eventRepository.findAll();
    }

}
