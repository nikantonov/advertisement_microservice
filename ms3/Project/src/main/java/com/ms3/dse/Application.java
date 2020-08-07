package com.ms3.dse;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * This is a Spring boot based RESTful application. I used 2 tutorials for this:
 * 1) http://spring.io/guides/gs/rest-service/
 * 2) http://spring.io/guides/gs/accessing-data-mysql/
 * Here is an application class, that simply runs the program:
 */
@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}

