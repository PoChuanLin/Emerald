# Backend-Challenge for NextGen_Emerald


## How to Build
```
  ./gradlew clean build
```
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


## Sample Tests

### Enrollee

```
curl -v -X PUT localhost:8080/enrollees/8 -H 'Content-type:application/json' -d '{"name":"Bar8", "activation":true, "dob":"2000-08-08", "phone":"888-456-7890"}'

curl -v -X GET localhost:8080/enrollees/3

curl -v -X POST localhost:8080/enrollees -H 'Content-type:application/json' -d '{"name":"Foo3", "activation":true, "dob":"2000-03-03", "phone":"222-456-7890"}'

curl -v -X POST localhost:8080/enrollees -H 'Content-type:application/json' -d '{"name":"Foo5", "activation":true, "dob":"2000-05-03", "phone":"555-456-7890"}'

curl -v -X DELETE localhost:8080/enrollees/4
```

###  Dependent

```
curl -v -X POST localhost:8080/dependents -H 'Content-type:application/json' -d '{"name":"D15", "dob":"2000-05-15", "enrollee":{"id":3 ,"name":"void", "activation":true, "dob":"2000-08-08"}}'

curl -v -X PUT localhost:8080/dependents/12 -H 'Content-type:application/json' -d '{"name":"D-12", "dob":"2000-12-01", "enrollee":{"id":3 ,"name":"void", "activation":true, "dob":"2000-08-08"}}'

curl -v -X GET localhost:8080/enrollees/3/dependents

curl -v -X DELETE localhost:8080/dependents/11

curl -v -X DELETE localhost:8080/enrollees/3/dependents

curl -v -X DELETE localhost:8080/dependents -H 'Content-type:application/json' -d '{"id":3 ,"name":"void", "activation":true, "dob":"2000-08-08"}'
```