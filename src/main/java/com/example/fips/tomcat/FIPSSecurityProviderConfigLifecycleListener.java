package com.example.fips.tomcat;

import org.apache.catalina.Lifecycle;
import org.apache.catalina.LifecycleEvent;
import org.apache.catalina.LifecycleListener;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.Provider;
import java.security.Security;

import org.bouncycastle.jcajce.provider.BouncyCastleFipsProvider;

/**
 * Springboot tomcat lifecycle listener to load the FIPS compliant JSSE.
 */
public class FIPSSecurityProviderConfigLifecycleListener implements LifecycleListener {
    private static final org.slf4j.Logger LOG = org.slf4j.LoggerFactory.getLogger(FIPSSecurityProviderConfigLifecycleListener.class);
    private static final String BC_FIP_CONFIG = "C:HYBRID;ENABLE{ALL};";
    static {
        boolean fipsMode = false;
        if ("true".equalsIgnoreCase(System.getProperty("org.bouncycastle.fips.approved_only"))) {
            fipsMode = true;
        }
        // Add BC-FIPS crypto provider
        Provider ccj = new BouncyCastleFipsProvider(BC_FIP_CONFIG);
        Security.insertProviderAt(ccj, 1);
        if (fipsMode) {
            LOG.info("FIPS mode enabled, Reconfiguring Sun JSSE");
            Security.removeProvider("SunJSSE");
            /*
            * Remove and re-add SunJSSE with BC ac the FIPS compliant crypto provider to enforce FIPS compliance for JSSE.
            * Need to see if there is a better way to configure Sun JSSE for FIPS mode programmatically.
             */
            @SuppressWarnings("restriction")
            Provider sunJSSE = new com.sun.net.ssl.internal.ssl.Provider(ccj);
            Security.addProvider(sunJSSE);
        }
        Provider[] providers = Security.getProviders();
        for (Provider provider : providers) {
            LOG.info("Provider: " + provider.getName() + " " + provider.getInfo());
        }

        /*
        * Log a verification message that confirms we are running in FIPS approved mode
         */
        try {
            MessageDigest.getInstance("TIGER");
            LOG.info("FIPS mode NOT enabled, TIGER hash algorithm available");
        } catch (NoSuchAlgorithmException nsa) {
            LOG.info("FIPS mode enabled, TIGER hash algorithm NOT available");
        }
    }

    @Override
    public void lifecycleEvent(LifecycleEvent lifecycleEvent) {
    }
}
