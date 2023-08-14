package com.example.fips;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Build a FIPS compliant spring boot rest controller.
 * ASSUMPTIONS: JAVA_HOME/lib/security/java.security is in BCFKS format. (reference https://www.veritas.com/support/en_US/article.100050833
 * B.  Converting the Java Certificate Authority keystore, cacerts:
 *
 *     Open an administrative command prompt in the folder containing the cacerts file for the current version of Java.
 *     For example:  C:\jdk-8u251-windows-x64\jre\lib\security folder in version 10.0
 *     Run the following command to convert the cacerts from JKS to BCFKS format:
 *     keytool -importkeystore -srckeystore cacerts  -srcstoretype JKS -srcstorepass changeit -destkeystore cacerts.bcfks -deststorepass changeit -deststoretype BCFKS -providerclass com.safelogic.cryptocomply.jcajce.provider.CryptoComplyFipsProvider
 *
 *    To convert the Java Certificate Authority keystore, cacerts using Keystore Explorer:
 *
 *     Open Keystore Explorer and use File > Open to navigate to the folder containing the cacerts file for the current version of Java.
 *     For example:  C:\jdk-8u251-windows-x64\jre\lib\security folder in version 10.0
 *     On the menu, open Tools > Change KeyStore Type and select BCFKS.
 *     On the menu, select File > Save As and name the file cacerts.bcfks
 *     Note: It is not necessary to rename the original cacerts file.  Other Java applications will use this file while eDiscovery will use the cacerts.bcfks.
 *     There is no need to restart services for this change to take effect.
 *
 *  - Implements the second approach documented in the README.md file.
 *  -- Uses the Bouncy Castle FIPS provider, reference pom.xml.
 *  -- Creates a custom LifecycleListener to load the FIPS compliant JSSE.
 *  -- Modifies the embedded tomcat connector to use the FIPS compliant JSSE.
 *  -- Modifies the embedded tomcat listener to load the FIPS compliant LifecycleListener.
 */
@SpringBootApplication
public class FipsApplication {

    public static void main(String[] args) {
        SpringApplication.run(FipsApplication.class, args);
    }
}
