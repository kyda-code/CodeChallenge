# Clip Challenge

## Prerequisites
- Spring Boot
- JPA
- H2
- Maven 4
- Java 11

## Description
This applications consist of the following:

- Controller package:  where the basic endpoint is.
- Repository package:  repository package for 
- Model package: where entities are stored.
- Request package: objects that represent request.


The project contains a simple API that saves a payment in an in-memory database (for the sake of this example lets use a in-memory-database).
The challenge consists of completing as many of the following steps as possible:

1. Create a new endpoint to list all users that have a payment saved in the database (information about payments should be already filled).
2. Create a new  endpoint so the api can support a disbursement process:
    - A disbursement process gets all transactions with status new and subtracts a fee of 3.5%  per transaction.
    - It updates all transactions with a status NEW  to PROCESSED.
    - Returns a list of users and the amount the'll get 
    -- Example - User_1 payment: 100, Disbursement: User_1:97.5 (Discount the fee)
3. Create an endpoint that returns a report per user:
    - Report:
    `{
      user_name - user name
      payments_sum: - sum of all payments (no mater what's the status)
      new_payments: sum of all new payments 
      new_payments_amount: sum of the amount of each payment
    }`
4. Add security for the disbursement process endpoint.


## Notes:
- The expected minimum is that you complete steps 1,2,3. 4 is optional  (Completing all is an extra).
- We want see your skills and abilities to code so if at any moment you want to change or refactor anything go ahead.
- We are considering as reviewers that your code challenge is code-prod-quality and it will review under this impression.
- Please initialize the directory with the challenge as a git repo so you can commit new features and we check on your thought process.
- Please upload the code-challenge to a git-repository and share the access with the reviewers thavt recruitment team indicates. 

## Test Cases:
The application contains different cases to testing:
- Register Success: To generate a new user that is granted access.
- Get All Payments Success: To get the array with all payments records.
- Get Payments Unauthorized: To validate the security of the endpoint.
- Get Payment Success: To get payment per id.
- Get Payment Not Found: To validate is a payment exists.
- Create Payment Success: To create a new payment return the id.
- Create Payment Missing Field: To validate the request.
- Update Payment Success: To update a payment.
- Delete Payment Success: To delete a payment.
- Get Report Per User: To get the summary of payments from a user.
- Disbursement Process: To calculate the fee per payment and return the new amount.

## Postman:
Contains a collection with Postman to generate the different use cases, each case has a script to add automatically the Bearer Token.

## BigDecimal for Finance:
Money or the type of monetary data is a fundamental part of many information systems; because the operations with it should be precise, and the roundings too.

BigDecimal is an exact way of representing numbers, and allows you to work with greater precision than, for example, the double data type.

## Swagger

Swagger is widely used for visualizing APIs, and with Swagger UI it provides online sandbox for frontend developers. For this application we use Springfox implementation of the Swagger 2 specification.

To access please click in the following link:
http://localhost:8080/swagger-ui/index.html

## Security 
JSON Web Token, or JWT (“jot”) for short, is a standard for safely passing claims in space constrained environments. It has found its way into all1 major web frameworks. Simplicity,
compactness and usability are key features of its architecture. Although much more complex systems are still in use, JWTs have a broad range of applications. 

For more information:
https://jwt.io/

## Database
H2 is an open-source lightweight Java database. It can be embedded in Java applications or run in the client-server mode. Mainly, H2 database can be configured to run as 
in-memory database, which means that data will not persist on the disk. Because of embedded database it is not used for production development, but mostly used for development and testing.

For more information:
https://www.h2database.com/html/main.html

## Docker Support
The Maven Wrapper is an easy way to ensure a user of your Maven build has everything necessary to run your Maven build.

Install the dependencies.
```sh
mvn -N wrapper:wrapper
```

Now we can run the application without the Docker container (that is, in the host OS):
```sh
./mvnw package && java -jar target/clip-0.0.1-SNAPSHOT.jar 
```

Dockerfile:
Running applications with user privileges helps to mitigate some risks (see, for example, a thread on StackExchange). So, an important improvement to the Dockerfile is to run the application as a non-root user:

```sh
FROM amazoncorretto:11-alpine-jdk
MAINTAINER Miguel Angel Sereno <msereno@kyda.mx>
ENV APP_HOME /app

# Run as non-root
RUN addgroup -S appuser && adduser -S appuser -G appuser
RUN mkdir -m 0755 -p ${APP_HOME}
RUN mkdir -m 0755 -p ${APP_HOME}/bin
RUN mkdir -m 0755 -p ${APP_HOME}/config
RUN mkdir -m 0755 -p ${APP_HOME}/logs
RUN chown -R appuser:appuser ${APP_HOME}
USER appuser

ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} ${APP_HOME}/bin/app.jar

WORKDIR ${APP_HOME}
ENTRYPOINT ["java","-jar","/app/bin/app.jar"]
```

Execute:
```sh
docker build -t clipio/clip-spring-boot-payments-docker .
docker run -p 8080:8080 clipio/clip-spring-boot-payments-docker
```

