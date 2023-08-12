package com.example.fips.tomcat;

import org.apache.catalina.connector.Connector;
import org.springframework.boot.web.embedded.tomcat.TomcatConnectorCustomizer;
//import org.springframework.boot.context.embedded.EmbeddedServletContainerCustomizer;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.boot.web.servlet.server.ConfigurableServletWebServerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.ResourceUtils;

import java.io.FileNotFoundException;

@Component
public class CustomEmbeddedConnector implements
        WebServerFactoryCustomizer<TomcatServletWebServerFactory> {
// OR    implements WebServerFactoryCustomizer<ConfigurableServletWebServerFactory> {

    @Override
    public void customize(TomcatServletWebServerFactory factory) {
        if (factory instanceof TomcatServletWebServerFactory) {
            customizeTomcat(factory);
        }
    }

    /**
     * Customize Tomcat's Connector element located in server.xml
     * @param factory
     */
    public void customizeTomcat(TomcatServletWebServerFactory factory) {
        factory.addConnectorCustomizers(new TomcatConnectorCustomizer() {
            @Override
            public void customize(Connector connector) {
                // set Connector attributes
                connector.setPort(4443);
                connector.setSecure(true);
                connector.setScheme("https");
                connector.setProperty("sslHostConfigSSLEnabled", "true");

                // set SSLHostConfig attritubes, in Connector
                connector.setProperty("sslHostConfigCertificateVerification", "optional");
                connector.setProperty("sslHostConfigTruststoreFile", "conf/tomcat-fips.bcks");
                connector.setProperty("sslHostConfigTruststorePassword", "This4Now");
                connector.setProperty("sslHostConfigTruststoreType", "BCFKS");   // REVISIT:  is this correct?
                connector.setProperty("sslHostConfigTruststoreProvider", "BCFIPS");  // REVISIT:  is this correct?

                // set Certificate attributes in SSLHostConfig
                connector.setProperty("sslHostConfigCertificateKeystoreFile", "conf/tomcat-fips.bcks");
                connector.setProperty("sslHostConfigCertificateKeystorePassword", "This4Now");
                connector.setProperty("sslHostConfigCertificateKeystoreType", "BCFKS");  // REVISIT:  is this correct?
                connector.setProperty("sslHostConfigCertificateKeystoreProvider", "BCFIPS");   // REVISIT:  is this correct?
            }
        });
    }

}
