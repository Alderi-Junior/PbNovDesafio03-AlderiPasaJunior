package com.compass.mseventmanager.dto;

import com.compass.mseventmanager.model.Event;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EventDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    private String id;
    private String eventName;
    private String dateTime;
    private String cep;
    private String logradouro;
    private String bairro;
    private String cidade;
    private String uf;


    public EventDTO(Event event){
        this.id = event.getId();
        this.eventName = event.getEventName();
        this.dateTime = event.getDateTime();
        this.cep = event.getCep();
        this.logradouro = event.getLogradouro();
        this.bairro = event.getBairro();
        this.cidade = event.getCidade();
        this.uf = event.getUf();
    }

    public EventDTO(String id, String eventName, String dateTime, String cep) {
        this.id = id;
        this.eventName = eventName;
        this.dateTime = dateTime;
        this.cep = cep;
        }
}
