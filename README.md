# Web Service for storing and searching notes (Demo)

## Stack
- Spring MVC
- Java config
- Java 8
- Maven
- Hibernate
- JPA
- PostgreSQL 9
- Tomcat 7
- REST
- JSON
- AssertJ
- JSP
- Ajax
- jQuery

## Setup database

Application properties

  $ notesSpringMvcDemo\src\main\resources\application.properties

Example of properties for PostgreSQL

  $ jdbc.driverClassName=org.postgresql.Driver
  $ jdbc.url=jdbc:postgresql://127.0.0.1:5432/notes 
  $ jdbc.username=Admin
  $ jdbc.password=

  $ jpa.database_platform=org.hibernate.dialect.PostgreSQLDialect
  $ jpa.show_sql=true
  $ jpa.generate_ddl=true

Create database 'notes'

## Logging

Log propertires 
 
  $ notesSpringMvcDemo\src\main\resources\log4j.properties

## International messages

  $ notesSpringMvcDemo\src\main\resources\messages_en.properties
  $ notesSpringMvcDemo\src\main\resources\messages_ru.properties
 

## Run
  $ mvn tomcat7:run

or 

  $ mvn jetty:run

or 

 $ mvn package 
and deploy notesSpringMvcDemo.war


## Page Url

http://localhost:8080/

or

http://localhost:8080/notesSpringMvcDemo/



