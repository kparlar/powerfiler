# Powerfiler
Spring Boot Test Project with Unit Test Coverage.
This is an example project for implementing a given use cases.
I have given a name Powerfiler, cause it is a profiler project for 
an given power and gas energy consumption use case. So Power & Profiler
combination Powerfiler fits to this application.

Have fun.



## Entity Model
Given below Powerfiler Project entity model. JPA is used as an api, and InheritanceType.JOINED type
strategy is used to make it generic for each entity classes, so inheritance is easily implemented to this
entity model.

![Entity Model](img/powerfiler_entity_model.PNG?raw=true "Entity Model")

## Database
H2 db is used, given below there is a image how to login the H2 db after starting the application
Tables are also given below.

![H2 Console for Powerfiler](img/h2_console.PNG?raw=true "H2 Console for Powerfiler")
![Dtabase Tables](img/db_tables.PNG?raw=true "Dtabase Tables")



## UnitTest
137 Unit Tests are written with %91 class coverage, %86 line coverage

![Unit Test Coverage](img/test-coverage.PNG?raw=true "Unit Test Coverage")

