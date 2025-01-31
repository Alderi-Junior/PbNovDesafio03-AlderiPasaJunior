package com.compass.msticketmanager.repositories;

import com.compass.msticketmanager.model.Event;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "event-api", url = "${msevent.events.event}")
public interface TicketClient {

    @GetMapping(value = "events/get-event/{id}")
    Event getEventById(@PathVariable("id") String id);

}
