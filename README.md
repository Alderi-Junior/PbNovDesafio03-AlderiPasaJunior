 # Compass Microservices Project

![Badge Status](http://img.shields.io/static/v1?label=STATUS&message=FINISH&color=GREEN&style=for-the-badge)

This is a microservices project developed with Java and Spring Boot. It consists of two main microservices: ms-event-manager and ms-ticket-manager. The project leverages RabbitMQ for message queuing and integrates with external APIs using OpenFeign.
## Overview

This project demonstrates the implementation of a microservices architecture using Spring Boot (version 3.3.x LTS). It includes:

ms-event-manager: Manages event-related operations.

ms-ticket-manager: Handles ticket management.

RabbitMQ: Facilitates communication between microservices through a message queue.

MongoDB Compass: Used as the database, with the following databases:

db_event

db_ticket

External API Consumption: ViaCEP API integration using OpenFeign.

AWS Services: EC2, VPC, Security Groups.

## 🛠️ Getting Started

To get a local copy of the project up and running, follow these steps:

1. Requirements:

- Java 17+
- Maven 3.8+
- RabbitMQ
- MongoDB Compass
  
2. Create the following databases in PostgreSQL:

- db_event
- db_ticket

3. Running the Microservices:
- Open the project and navigate to each microservice module.

- Run the Spring Boot applications individually using Maven or your IDE.

4. Running Tests

- The project includes unit tests with a minimum 80% line coverage.

- Run tests using:

- mvn test

📡 Messaging & API Integration

- RabbitMQ is used for asynchronous messaging between microservices.

- OpenFeign is used as an HTTP client for external API communication (e.g., ViaCEP for address lookup).

🚀 Deployment

- The microservices can be deployed on AWS using EC2.

- VPC and Security Groups are configured for secure networking.

RabbitMQ and MongoDB can be hosted in a cloud environment or run locally.

## API Endpoints Description
### ms-event-manager
- POST `/events/post-new-event` - Creates a new event.
```json
{
  "eventName": "Show da Xuxa",
  "dateTime": "2024-12-30T21:00:00",
  "cep": "01020-000"
}
```
- GET `/events/get-event/{id}` - Retrieves a event by their ID.
- GET `/events/all-events/{id}` - Retrieves all events.
- GET `/events/all-events-sorted/{id}` - Retrieves all events sorted by name.
- PUT `/events/update-event/{id}` - Updates event information by their ID.
- DELETE `/events/delete-event/{id}` - Deletes a event by id and verify if have some active ticket.
```json
{
  "eventName": "Show da Xuxa e Ozzy",
}
```
### ms-ticket-manager
- POST `/ticket/post-new-ticket` - Creates a new ticket with a valid eventId.
```json
{
"ticketId": "string", (auto generate - sequencial)
  "customerName": "string",
  "cpf": "string",
  "customerMail": "string",
  "eventId": "string",
  "eventName": "string",
  "BRLamount": "R$ 50,00",
  "USDamount": "$ 10,00",
}
```
- GET `/tickets/get-tickets/{id}` - Retrieves a tickets by their ID.
- GET `/tickets/get-ticket-by-cpf{id}` - Retrieves all tickets by CPF.
- GET `/check-tickets-by-event/{eventId}` - Retrieves all tickets vinculated with a eventId.
- DELETE `/tickets/delete-tickets/{id}` - SOFT Deletes a ticket by updating the status to "Canceled".
- PUT `/tickets/update-tickets/{id}` - Updates tickets information by their ID.
```json
{
  "customerName": "Jose Francisco",
}
```
