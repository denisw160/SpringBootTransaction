# SpringBootTransaction

This demo project shows you the transaction handling in Spring Boot with Spring Data (JPA).

## Build

The project is configured for Maven and can build with `mvn package`. If maven is not installed, you can use
`mvnw package` to get maven online.

The build works for Java 11+

## Run

The result is a standalone jar for executing the application server. Just run `java -jar /target/spring-tranaction.jar`.

## Program

After starting the application you can open the UI / testing scenarios in your browser: `http://localhost:8080`

## Database

For query the database you can open the url `http://localhost:8080/h2` in your browser. For file based database please
use this jdbc url: `jdbc:h2:file:./db/h2`

## Reference Documentation

For further reference, please consider the following sections:

* [Official Apache Maven documentation](https://maven.apache.org/guides/index.html)
* [Spring Boot Maven Plugin Reference Guide](https://docs.spring.io/spring-boot/docs/2.5.0/maven-plugin/reference/html/)
* [Create an OCI image](https://docs.spring.io/spring-boot/docs/2.5.0/maven-plugin/reference/html/#build-image)
* [Spring Boot DevTools](https://docs.spring.io/spring-boot/docs/2.5.0/reference/htmlsingle/#using-boot-devtools)
* [Spring Web](https://docs.spring.io/spring-boot/docs/2.5.0/reference/htmlsingle/#boot-features-developing-web-applications)
* [Spring Data JPA](https://docs.spring.io/spring-boot/docs/2.5.0/reference/htmlsingle/#boot-features-jpa-and-spring-data)

## Guides

The following guides illustrate how to use some features concretely:

* [Building a RESTful Web Service](https://spring.io/guides/gs/rest-service/)
* [Serving Web Content with Spring MVC](https://spring.io/guides/gs/serving-web-content/)
* [Building REST services with Spring](https://spring.io/guides/tutorials/bookmarks/)
* [Accessing Data with JPA](https://spring.io/guides/gs/accessing-data-jpa/)

