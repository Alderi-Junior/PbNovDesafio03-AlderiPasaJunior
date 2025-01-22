package com.compass.mseventmanager.dto;

import com.compass.mseventmanager.model.Event;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

@Data
@NoArgsConstructor
public class EventDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    private String id;
    private String eventName;
    private Date LocalDateTime;
    private String cep;
    private String logradouro;
    private String bairro;
    private String cidade;
    private String uf;


    public EventDTO(Event event){
        this.id = event.getId();
        this.eventName = event.getEventName();
        this.cep = event.getCep();
        this.logradouro = event.getLogradouro();
        this.bairro = event.getBairro();
        this.cidade = event.getCidade();
        this.uf = event.getUf();
    }
}
