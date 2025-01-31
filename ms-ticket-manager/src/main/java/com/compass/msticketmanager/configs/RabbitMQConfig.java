package com.compass.msticketmanager.configs;

import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;

@Configuration
public class RabbitMQConfig {

    @Value("${mq.queues.ms-ticket}")
    private String queueSend;

    @Bean
    public Queue queueTickets() {
        return new Queue(queueSend, true);
    }
}
