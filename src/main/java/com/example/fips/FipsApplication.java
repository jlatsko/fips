package com.example.fips;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Build a FIPS compliant spring boot rest controller.
 *  - Implements the second approach documented in the README.md file.
 *  -- Uses the Bouncy Castle FIPS provider, reference pom.xml.
 *  -- Creates a custom LifecycleListener to load the FIPS compliant JSSE.
 *  -- Modifies the embedded tomcat connector to use the FIPS compliant JSSE.
 */
@SpringBootApplication
public class FipsApplication {

    public static void main(String[] args) {
        SpringApplication.run(FipsApplication.class, args);
    }

}
