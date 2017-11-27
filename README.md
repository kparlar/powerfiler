# Powerfiler
Spring Boot Test Project with Unit Test Coverage.
This is an example project for implementing a given use cases.
I have given a name Powerfiler, cause it is a profiler project for 
an given power and gas energy consumption use case. So Power & Profiler
combination Powerfiler fits to this application.

Have fun.

## Api End Points
For detailed api check Swagger please, swagger annotation is used in this project.
You can check it from this link, http://localhost:8000/swagger-ui.html#/
Also here is the screenshot for swagger.

![Swagger](img/api.PNG?raw=true "Swagger")



## Postman Collections 
Postman Collections are in the postman folder. There is a screenshot below.


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


## Sheduled Task for CSV files
There is a scheduled task created in this project too. When you run the spring-boot project, there is fixed delay period of 15 second 
in each run, the scheduled task will read the  C://Temp/powerfiler/  folder and try to ingest data in csv files.
If it is successfully ingested, deleted the file. And if any error is occurred than it logs to the same folder.
Schedule task first change the file name with 'KEPT' like turning MeterDat.csv to MeterDatas_2743c533-b784-4951-90cd-27ef80ae041a_KEPT.csv
and after that processes the file. If there is any error occured, this file is left in the same folder with KEPT filename.
You can use this with test csv file under csv folder, name MeterDatas.csv.  






