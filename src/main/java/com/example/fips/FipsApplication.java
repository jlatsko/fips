package com.example.fips;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Build a FIPS compliant spring boot rest controller
 *
 */
@SpringBootApplication
public class FipsApplication {

    public static void main(String[] args) {
        SpringApplication.run(FipsApplication.class, args);
    }

}
