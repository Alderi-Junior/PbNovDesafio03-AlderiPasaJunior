package com.compass.mseventmanager.services;


import com.compass.mseventmanager.model.Event;
import com.compass.mseventmanager.repositories.AddressFeign;
import com.compass.mseventmanager.repositories.EventRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class EventSeviceTest {

    @Mock
    private EventRepository eventRepository;

    @InjectMocks
    private EventService eventService;

    private Event event;

    @BeforeEach
    void setUp() {
        event = new Event("1", "Event Name", LocalDateTime.parse("2025-01-01T10:00:00"), "12345678"
                        , "Rua Teste", "Bairro Teste", "Cidade Teste", "UF");
    }

    @Test
    void testFindAll() {
        when(eventRepository.findAll()).thenReturn(Arrays.asList(event));
        List<Event> events = eventService.findAll();
        assertEquals(1, events.size());
        assertEquals(event, events.get(0));
    }

}