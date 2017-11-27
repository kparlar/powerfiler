# Powerfiler
Spring Boot Test Project with Unit Test Coverage.
This is an example project for implementing a given use cases.
I have given a name Powerfiler, cause it is a profiler project for 
an given power and gas energy consumption use case. So Power & Profiler
combination Powerfiler fits to this application.

Have fun.

## Api End Points
For detailed api check Swagger please, swagger annotation is used in this project.
Postman collections are in the postman folder. There is a screenshot below.

### For App operations
/api/app/powerfiler/v1/connections

1. /api/app/powerfiler/v1/connections/{connectionId}/consumption?startMonth={startMonth}&endMonth={endMonth} [GET] Consumption of given
connectionId within given start and end month

### For Data operations
/api/data/powerfiler/v1/connections

1. /api/app/powerfiler/v1/connections  [put] : Ingest Connection Data with Profile, Meters, Fractions
2. /api/data/powerfiler/v1/connections/{connectionId}/meters [PUT] Ingest Meters data
3. /api/data/powerfiler/v1/connections/{connectionId}/fractions [PUT] Ingest Fractions data
4. /api/data/powerfiler/v1/connections/{connectionId}/meters/{month} [DELETE] Delete Meter given connectionId and month
5. /api/data/powerfiler/v1/connections/{connectionId}/fractions/{month} [DELETE] Delete Fraction given connectionId and month
6. /api/data/powerfiler/v1/connections/{id} [GET] Get Connection with id.
7. /api/data/powerfiler/v1/connections/0001/meters [GET] Get All Meters related with connection Id
8. /api/data/powerfiler/v1/connections/0001/fractions [GET] Get All Fraction related with connection Id

 ![Postman Request Samples](img/powerfiler_postman.PNG?raw=true "Postman Request Samples")


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

## How To Run
After git clone, you can directly run from your ide as any spring-boot project. When the application stated
you can use the postman request to insert data or get data.
Given below the request samples.





