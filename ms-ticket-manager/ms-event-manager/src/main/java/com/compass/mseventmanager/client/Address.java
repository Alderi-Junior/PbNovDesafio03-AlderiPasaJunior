package com.compass.mseventmanager.client;

public record Address (

    String cep,
    String logradouro,
    String bairro,
    String localidade,
    String uf
    )
{
    public Address(String logradouro, String bairro, String localidade, String uf) {
        this(null, logradouro, bairro, localidade, uf);
    }
}
