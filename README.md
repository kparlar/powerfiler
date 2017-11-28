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
Or if you want to run jar, first you have to clone the project from repository, inside the powerfiler folder run first
**mvn package** this will build a jar called **powerfiler-0.0.1-SNAPSHOT.jar** under target folder. 
Than you can run the application with  **java -jar powerfiler-0.0.1-SNAPSHOT.jar** command.

when the service is on, it will host on port 8000. First you have to ingest data
please request the postman   data >> Connections  PUT service  **http://localhost:8000/api/data/powerfiler/v1/connections** this one
it will directly populated dummy data so you can easily get the data, if you want add another data, feel free with modify the body, but 
please be careful with the format.

Ex: to get consumption run the app >> Consumption Given Start - End Month Interval  request like **http://localhost:8000/api/app/powerfiler/v1/connections/0001/consumption?startMonth=JANUARY&endMonth=MAY**
this one.
If you have any question feel free to ask.
 


## Sheduled Task for CSV files
There is a scheduled task created in this project too. When you run the spring-boot project, there is fixed delay period of 15 second 
in each run, the scheduled task will read the  C://Temp/powerfiler/  folder and try to ingest data in csv files.
If it is successfully ingested, deleted the file. And if any error is occurred than it logs to the same folder.
Schedule task first change the file name with 'KEPT' like turning MeterDat.csv to MeterDatas_2743c533-b784-4951-90cd-27ef80ae041a_KEPT.csv
and after that processes the file. If there is any error occured, this file is left in the same folder with KEPT filename.
You can use this with test csv file under csv folder, name MeterDatas.csv.  


## Code Quality
There are 27 sonar issues left to resolve , they are not a big issue but needs time to solve them.
![SONAR](img/sonar.PNG?raw=true "SONAR")



