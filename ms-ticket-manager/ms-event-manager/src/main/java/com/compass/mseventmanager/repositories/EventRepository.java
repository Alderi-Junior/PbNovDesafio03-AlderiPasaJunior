package com.compass.mseventmanager.repositories;

import com.compass.mseventmanager.model.Event;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EventRepository extends MongoRepository<Event, String> {
}
