# Spring Boot Travel Booking API

## Overview

Spring Boot Travel Booking API is a Java backend application for a vacation booking system. The project provides RESTful backend functionality for managing vacation packages, excursions, customers, carts, checkout, and order tracking.

The application was built with Spring Boot and integrates with a MySQL database. It is designed to support an existing frontend application by exposing backend services for vacation package browsing and customer checkout.

This project was created for academic learning and portfolio development. It demonstrates backend development with Java, Spring Boot, Spring Data JPA, RESTful APIs, relational database integration, and service-layer design.

## Project Purpose

The purpose of this project is to modernize the backend of a travel booking system using the Spring Framework. The application provides the backend logic needed for a vacation booking platform where customers can select vacation packages, add excursions, and complete a checkout process.

The project demonstrates how a legacy-style backend can be migrated into a modern Spring Boot architecture with clearly organized layers for entities, repositories, services, controllers, and configuration.

## Features

* Spring Boot backend application
* REST API support for travel booking data
* MySQL database integration
* Spring Data JPA repositories
* Checkout service with purchase response
* Order tracking number generation
* Customer, cart, vacation, and excursion entities
* Entity relationships mapped with JPA annotations
* Cross-origin support for frontend integration
* Input validation for required frontend data
* Programmatic sample customer creation
* Layered backend architecture
* Lombok support for reducing boilerplate code

## Technologies Used

* Java
* Spring Boot
* Spring Data JPA
* Spring Data REST
* MySQL
* Lombok
* Maven
* IntelliJ IDEA
* Git
* GitHub

## Application Architecture

The backend follows a layered architecture:

```text
spring-boot-travel-booking-api/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── .../
│   │   │       ├── config/
│   │   │       ├── controllers/
│   │   │       ├── dao/
│   │   │       ├── entities/
│   │   │       └── services/
│   │   │
│   │   └── resources/
│   │       └── application.properties
│   │
│   └── test/
│       └── java/
│
├── pom.xml
└── README.md
```

## Package Responsibilities

### `config`

Contains backend configuration classes used to support REST data exposure and application setup.

### `controllers`

Contains REST controller classes that expose backend endpoints to the frontend application.

The checkout controller handles purchase requests and returns order tracking information.

### `dao`

Contains repository interfaces that extend Spring Data JPA repository functionality.

These repositories provide database access for the application entities.

### `entities`

Contains the domain model classes used by the application.

These classes represent the core business objects in the travel booking system.

### `services`

Contains service-layer classes and interfaces.

The service layer handles checkout logic, purchase processing, cart handling, and order tracking number generation.

## Core Domain Objects

The application includes backend support for travel booking entities such as:

* Customers
* Carts
* Cart items
* Vacations
* Excursions
* Divisions
* Countries
* Order status values

These entities are mapped to relational database tables using JPA annotations.

## Checkout Workflow

The checkout process follows this general flow:

1. The frontend sends a purchase request to the backend.
2. The backend receives customer, cart, and cart item data.
3. The checkout service validates and processes the purchase.
4. A tracking number is generated for the order.
5. The cart and related items are saved to the database.
6. The backend returns a purchase response containing the order tracking number.

## API Functionality

The backend supports RESTful functionality for the travel booking application.

Example functionality includes:

* Retrieving vacation packages
* Retrieving excursions
* Managing customer checkout
* Creating cart records
* Saving cart items
* Returning order tracking numbers

The checkout endpoint is responsible for placing customer orders.

Example endpoint:

```text
/api/checkout/purchase
```

## Database

The application uses MySQL for persistent data storage.

The database stores information related to:

* Customers
* Vacation packages
* Excursions
* Shopping carts
* Cart items
* Order tracking
* Countries and divisions

The application uses Spring Data JPA to map Java entities to database tables and manage persistence.

## Validation

The backend includes validation to help ensure that required data is present when the frontend submits checkout information.

Validation helps prevent incomplete or invalid purchase requests from being processed.

## Sample Data

The application includes programmatic sample customer creation. Sample data is added in a way that prevents existing customer data from being overwritten each time the application runs.

This allows the application to maintain test data while avoiding duplicate or destructive inserts during repeated development runs.

## Running the Application

### Prerequisites

Make sure the following are installed and configured:

* Java JDK
* Maven
* MySQL
* MySQL Workbench or another database client
* IntelliJ IDEA or another Java IDE

### Database Setup

Before running the backend, make sure MySQL is running and the database connection settings in `application.properties` match your local environment.

Typical settings include:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/your_database_name
spring.datasource.username=your_username
spring.datasource.password=your_password
```

### Run with Maven

From the project root directory, run:

```bash
./mvnw spring-boot:run
```

On Windows, use:

```bash
mvnw.cmd spring-boot:run
```

### Run in IntelliJ IDEA

1. Open the project in IntelliJ IDEA.
2. Allow Maven to load the project dependencies.
3. Confirm the MySQL database connection settings.
4. Run the main Spring Boot application class.
5. Confirm the backend starts successfully.
6. Use the frontend or an API client to test backend functionality.

The backend usually runs at:

```text
http://localhost:8080
```

## Example Checkout Response

A successful checkout request returns a response containing an order tracking number.

Example response:

```json
{
  "orderTrackingNumber": "abc123-def456"
}
```

## Portfolio Highlights

This project demonstrates experience with:

* Java backend development
* Spring Boot application structure
* REST API development
* Object-oriented programming
* Layered architecture
* JPA entity modeling
* Repository pattern
* Service-layer design
* MySQL database integration
* Backend validation
* Checkout workflow implementation
* Maven project management
* Git and GitHub version control

## Future Enhancements

Possible future improvements include:

* Add expanded unit and integration tests
* Add Swagger/OpenAPI documentation
* Add user authentication and authorization
* Add admin endpoints for managing vacations and excursions
* Add improved exception handling
* Add logging for checkout and database operations
* Add Docker support for local development
* Add database migration management with Flyway or Liquibase
* Add deployment configuration
* Add CI/CD workflow automation
* Build a custom frontend dashboard

## Academic and Portfolio Note

This project was created for academic learning and portfolio development. It is intended to demonstrate backend programming skills, Spring Boot development, database integration, and REST API design.

## Author

Drum Holliday

## License

This project is currently for educational and portfolio purposes. A formal license can be added later if the project is prepared for public reuse.
