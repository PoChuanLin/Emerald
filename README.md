# Backend-Challenge for NextGen_Emerald


## How to Build

To build jar
```
  ./gradlew clean build
```

To build Docker image
```
  ./gradlew bootBuildImage
```
(edit ```bootBuildImage``` section in ```build.gradle``` to customerize image name.)

This build is optimized for Spring Boot application, with high-efficent layered Docker image.
(No ```Dockerfile``` is needed.)


## How to Run
```
  ./gradlew bootRun
```
 or
```
  java -jar build/libs/emerald-0.0.1-SNAPSHOT.jar
```


## Database Requirement

The program expects a MySQL database at
```
  jdbc:mysql://localhost:3306/emerald
```
with user/password as  ```developer/developer```

Please edit an ```application.properties``` file to configure.


## REST WebService API

Please see the Swagger UI page for details, after the service started.

http://localhost:8080/swagger-ui/#/


For service API, browser to these pages

http://localhost:8080/swagger-ui/#/enrollee-controller

and

http://localhost:8080/swagger-ui/#/dependent-controller




## Actuator

Actuator is turned on in development configuration, 
be sure to turn it off in production by deleting its configuration.

http://localhost:8080/actuator/

http://localhost:8080/actuator/health

http://localhost:8080/actuator/env

http://localhost:8080/actuator/beans


## Sample Tests

### Enrollee

```
curl -v -X POST localhost:8080/enrollees -H 'Content-type:application/json' -d '{"name":"Foo27", "activation":true, "dob":"2000-03-27", "phone":"222-456-7890"}'

curl -v -X PUT localhost:8080/enrollees/8 -H 'Content-type:application/json' -d '{"name":"Bar8", "activation":true, "dob":"2000-08-08", "phone":"888-456-7890"}'

curl -v -X GET localhost:8080/enrollees/3

curl -v -X DELETE localhost:8080/enrollees/26

```

###  Dependent

```
curl -v -G -X POST localhost:8080/enrollees/29/dependents --data-urlencode 'dob=2007-12-06' --data-urlencode 'name=D29G'

curl -v -X POST localhost:8080/dependents -H 'Content-type:application/json' -d '{"name":"D29C", "dob":"2000-05-23", "enrollee":{"id":29 ,"name":"void", "activation":true, "dob":"2000-08-23"}}'

curl -v -X PUT localhost:8080/dependents/12 -H 'Content-type:application/json' -d '{"name":"D-12", "dob":"2000-12-01", "enrollee":{"id":3 ,"name":"void", "activation":true, "dob":"2000-08-08"}}'

curl -v -X GET localhost:8080/enrollees/3/dependents

curl -v -X DELETE localhost:8080/dependents/31

curl -v -X DELETE localhost:8080/enrollees/29/dependents

```