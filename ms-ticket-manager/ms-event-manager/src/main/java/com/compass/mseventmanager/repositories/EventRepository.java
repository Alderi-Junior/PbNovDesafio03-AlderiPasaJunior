package com.compass.mseventmanager.repositories;

import com.compass.mseventmanager.model.Event;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EventRepository extends MongoRepository<Event, String> {

    List<Event> findAllByOrderByEventNameAsc();
}
