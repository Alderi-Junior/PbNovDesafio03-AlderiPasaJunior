package com.compass.msticketmanager.repositories;

import com.compass.msticketmanager.model.Event;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "event-api", url = "http://localhost:8090/events/")
public interface TicketClient {

    @GetMapping(value = "/get-event/{id}")
    Event getEventById(@PathVariable("id") String id);

}
