package com.example.fips;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Build a FIPS compliant spring boot rest controller.
 * modify the spring boot tomcat server.xml to use the FIPS compliant JSSE.
 * 1. download the FIPS compliant JSSE from oracle.
 * 2. unzip the file.
 * 3. copy the fips.jar to the tomcat lib folder.
 * 4. modify the server.xml to use the FIPS compliant JSSE.
 * 5. run the spring boot application.
 * 6. call the rest controller.
 * 7. the rest controller returns the FIPS compliant JSSE version.
 * 8. the FIPS compliant JSSE version is
 *
 */
@SpringBootApplication
public class FipsApplication {

    public static void main(String[] args) {
        SpringApplication.run(FipsApplication.class, args);
    }

}
