# Inditex Technical Test
## Overview
This SpringBoot application provides a REST endpoint to retrieve product prices based on input parameters such as application date, product ID, and brand ID. The response includes the product ID, brand ID, applicable price list, date range, and final price. An in-memory H2 database is initialized with sample data for quick testing.
## Technologies
The technologies used in this project are:

+ Spring Boot (version 3.3.4)
+ Spring Boot Starter Web
+ Spring Boot Starter Data JPA
+ H2 Database
+ Lombok (version 1.18.34)
+ Spring Boot Starter Test
+ Maven
  
### Architecture
The project uses a hexagonal architecture (also known as ports and adapters) to promote flexibility and separation of concerns. It decouples business logic from external systems, such as databases or APIs, by using interfaces (ports) and concrete implementations (adapters). This makes it easier to test, maintain and adapt to future changes, ensuring a more modular and scalable application design.
### Design Pattern
In this project, the singleton pattern is used in the PricesServiceImpl class to ensure that only one instance of the service exists throughout the application lifecycle. This approach ensures consistency of pricing logic, optimises resource usage and provides a single point of access to the service. It prevents the creation of multiple instances, reducing memory and processing overhead.
## Launch
To run the application, it is necessary to clone the repository to a local machine. The database is already included in the project, as an H2 in-memory database configured in a data.sql file has been used, and Spring is configured by default to load it during initialisation. 
There are five tests in the test section, four of which check that the data is returned correctly if the data entered for a product and a brand is correct and returns the expected data. A final test has been added to check the operation of the application when the input data entered does not exist in the database.
