package com.compass.msticketmanager.repositories;

import com.compass.msticketmanager.model.Ticket;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TicketRepository extends MongoRepository<Ticket, String> {
    List<Ticket> findByCpf(String cpf);
    List<Ticket> findByEventId(String eventId);
}
