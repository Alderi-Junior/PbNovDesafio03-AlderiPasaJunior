package com.compass.mseventmanager.model;


import lombok.*;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "events")
public class Event{

    @Id
    private String id;
    private String eventName;
    private Date LocalDateTime;
    private String cep;
    private String logradouro;
    private String bairro;
    private String cidade;
    private String uf;

    public Event(String Id, String eventName, String cep) {
        this.id = Id;
        this.eventName = eventName;
        this.cep = cep;
    }

}
