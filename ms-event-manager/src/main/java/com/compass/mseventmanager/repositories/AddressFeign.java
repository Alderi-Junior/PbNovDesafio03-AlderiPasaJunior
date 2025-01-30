package com.compass.mseventmanager.repositories;

import com.compass.mseventmanager.client.Address;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


@FeignClient(url = "https://viacep.com.br/ws/", name = "viacep")
public interface AddressFeign {

    @GetMapping("{cep}/json")
    Address getAddress(@PathVariable String cep);
}
