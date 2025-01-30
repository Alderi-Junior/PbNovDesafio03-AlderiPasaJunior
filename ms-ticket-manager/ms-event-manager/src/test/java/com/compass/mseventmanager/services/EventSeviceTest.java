package com.compass.mseventmanager.services;


import com.compass.mseventmanager.client.Address;
import com.compass.mseventmanager.dto.EventDTO;
import com.compass.mseventmanager.model.Event;
import com.compass.mseventmanager.repositories.AddressFeign;
import com.compass.mseventmanager.repositories.EventRepository;
import com.compass.mseventmanager.repositories.TicketClient;
import com.compass.mseventmanager.services.exception.ObjectNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;


import java.util.Arrays;
import java.util.List;
import java.util.Optional;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class EventSeviceTest {

    @Mock
    private EventRepository eventRepository;

    @InjectMocks
    private EventService eventService;

    private Event event;

    @Mock
    private AddressFeign addressFeign;

    @Mock
    private TicketClient ticketClient;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        event = new Event("1", "Event Name", "2025-01-01T10:00:00", "12345678"
                        , "Rua Teste", "Bairro Teste", "Cidade Teste", "UF");
    }

    @Test
    void testFindAll() {
        when(eventRepository.findAll()).thenReturn(Arrays.asList(event));
        List<Event> events = eventService.findAll();
        assertEquals(1, events.size());
        assertEquals(event, events.get(0));
    }

    @Test
    void testFindById() {
        when(eventRepository.findById(anyString())).thenReturn(Optional.of(event));
        Event foundEvent = eventService.findById("1");
        assertNotNull(foundEvent);
        assertEquals(event, foundEvent);
    }

    @Test
    void testFindById_NotFound() {
        when(eventRepository.findById(anyString())).thenReturn(Optional.empty());
        assertThrows(ObjectNotFoundException.class, () -> eventService.findById("2"));
    }

    @Test
    void testInsert() {
        when(addressFeign.getAddress(anyString())).thenReturn(new Address("Rua Teste", "Bairro Teste", "Cidade Teste", "UF"));
        when(eventRepository.insert(any(Event.class))).thenReturn(event);
        Event insertedEvent = eventService.insert(event);
        assertNotNull(insertedEvent);
        assertEquals(event, insertedEvent);
    }

    @Test
    void testDelete_WithTickets() {
        when(ticketClient.findTicketsByEvent(event.getId())).thenReturn(Arrays.asList());
        eventService.delete(event.getId());
        verify(eventRepository, times(1)).deleteById(event.getId());
        verify(ticketClient, times(1)).findTicketsByEvent(event.getId());
    }

    @Test
    void testDelete_NoTickets() {
        when(ticketClient.findTicketsByEvent(event.getId())).thenReturn(Arrays.asList());
        eventService.delete(event.getId());
        verify(eventRepository, times(1)).deleteById(event.getId());
        verify(ticketClient, times(1)).findTicketsByEvent(event.getId());
    }

    @Test
    void testUpdate() {
        when(eventRepository.findById(anyString())).thenReturn(Optional.of(event));
        when(eventRepository.save(any(Event.class))).thenReturn(event);
        Event updatedEvent = eventService.update(event);
        assertNotNull(updatedEvent);
        assertEquals(event, updatedEvent);
    }

    @Test
    void testUpdate_NotFound() {
        when(eventRepository.findById(anyString())).thenReturn(Optional.empty());
        assertThrows(ObjectNotFoundException.class, () -> eventService.update(event));
    }

    @Test
    void testFindAllWSort() {
        when(eventRepository.findAllByOrderByEventNameAsc()).thenReturn(Arrays.asList(event));
        List<Event> sortedEvents = eventService.findAllWSort();
        assertEquals(1, sortedEvents.size());
        assertEquals(event, sortedEvents.get(0));
    }

    @Test
    void testFromDTO() {
        EventDTO eventDTO = new EventDTO("1", "Event Name","2025-01-01T10:00:00", "12345678", "Rua Teste", "Bairro Teste", "Cidade Teste", "UF");

        Event event = eventService.fromDTO(eventDTO);

        assertNotNull(event);
        assertEquals(eventDTO.getId(), event.getId());
        assertEquals(eventDTO.getEventName(), event.getEventName());
        assertEquals(eventDTO.getDateTime(), event.getDateTime());
        assertEquals(eventDTO.getCep(), event.getCep());
        assertEquals(eventDTO.getLogradouro(), event.getLogradouro());
        assertEquals(eventDTO.getBairro(), event.getBairro());
        assertEquals(eventDTO.getCidade(), event.getCidade());
        assertEquals(eventDTO.getUf(), event.getUf());
    }
}