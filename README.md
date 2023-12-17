## Study project with Hibernate and Docker.

### Overview
#### Simple application to setup relation with JPA/HQL and DB on select-query without problem N+1.
#### We have a relational MySQL database with a schema (country-city, language per country). There's a frequent query for cities that is causing performance issues. We've come up with a solution - to offload all frequently queried data into Redis (an in-memory key-value storage). Also testing time on queries between MySQL and Redis.
###
#### MySQL and Redis start via Docker container.
### 
#### Technology stack:

- Java 18
- Maven 4.0.0
- Hibernate 5.6.15.Final
- MySQL-connector 8.0.33
- p6spy 3.9.1
- Junit 5.9.2

