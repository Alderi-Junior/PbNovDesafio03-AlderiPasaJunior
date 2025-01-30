package com.compass.msticketmanager.infra.mqueue;

import com.compass.msticketmanager.dto.TicketDto;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TicketEmissionPublisher {

    private final RabbitTemplate rabbitTemplate;
    private final Queue ticketEmissionQueue;

    public void publish( TicketDto ticketDtoResponse) throws JsonProcessingException {
        var json = convertTicketToJson(ticketDtoResponse);
        rabbitTemplate.convertAndSend(ticketEmissionQueue.getName(), json);
    }

    private String convertTicketToJson(TicketDto ticketDto ) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        var json = objectMapper.writeValueAsString(ticketDto);
        return json;
    }
}
