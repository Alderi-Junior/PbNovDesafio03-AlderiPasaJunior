package com.compass.mseventmanager.services.exception;

public class EventWithActiveTicketsException extends RuntimeException {
    public EventWithActiveTicketsException(String message) {
        super(message);
    }
}
