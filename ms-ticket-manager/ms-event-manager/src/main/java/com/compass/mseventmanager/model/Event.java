package com.compass.mseventmanager.model;


import lombok.*;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;


import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "events")
public class Event{

    @Id
    private String id;
    private String eventName;
    private LocalDateTime dateTime;
    private String cep;
    private String logradouro;
    private String bairro;
    private String cidade;
    private String uf;

    public Event(String eventName, LocalDateTime dateTime, String cep) {
        this.eventName = eventName;
        this.cep = cep;
        this.dateTime = dateTime;

    }
}
