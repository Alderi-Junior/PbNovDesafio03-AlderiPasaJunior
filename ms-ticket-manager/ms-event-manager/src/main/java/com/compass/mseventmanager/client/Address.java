package com.compass.mseventmanager.client;

public record Address (

    String cep,
    String logradouro,
    String bairro,
    String localidade,
    String uf
    )
{}
