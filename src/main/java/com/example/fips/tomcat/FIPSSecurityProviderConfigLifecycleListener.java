package com.example.fips.tomcat;

import org.apache.catalina.LifecycleEvent;
import org.apache.catalina.LifecycleListener;

import java.security.Provider;
import java.security.Security;

import org.bouncycastle.jcajce.provider.BouncyCastleFipsProvider;

/**
 * Springboot tomcat lifecycle listener to load the FIPS compliant JSSE.
 */
public class FIPSSecurityProviderConfigLifecycleListener implements LifecycleListener {
    private static final org.slf4j.Logger LOG = org.slf4j.LoggerFactory.getLogger(FIPSSecurityProviderConfigLifecycleListener.class);
//    static {
//        //Get all providers configured in java.security file in JVM (jre\lib\security\java.security)
//
//        Provider[] providers = Security.getProviders();
//
//        LOG.info("Removing all providers configured in JVM (java.security file) ");
//        if (providers != null && providers.length > 0) {
//            for(int i = 0; i < providers.length; i++ ) {
//                // Don't remove Sun SSL provider - SunJSSE, we still are using SunJSSE but we can remove it if using JSSE provided by Bouncy Castle.
//                if (!providers[i].getName().equalsIgnoreCase("SunJSSE")) {
//                    LOG.info("Removing provider: " +providers[i].getName() + " Info: " +providers[i].getInfo());
//                    // Remove all providers
//                    Security.removeProvider(providers[i].getName());
//                }
//            }
//
//        }
//        //From the documentation - At the moment the configuration string is limited to setting the DRBG. The configuration string
//        //must always start with “C:” and finish with “ENABLE{ALL};”.
//        //In situations where the amount of entropy is constrained the default DRBG for the provider can be
//        //configured to use an DRBG chain based on a SHA-512 SP 800-90A DRBG as the internal DRBG
//        //providing a seed generation. To configure this use:
//        //“C:HYBRID;ENABLE{All};”
//        Security.insertProviderAt(new BouncyCastleFipsProvider("C:HYBRID;ENABLE{All};"),1);
//        //This is equivalent of security.provider.2=com.sun.net.ssl.internal.ssl.Provider BCFIPS
//        //Most likely not needed and can be omitted.
//        //Security.addProvider(new com.sun.net.ssl.internal.ssl.Provider("BCFIPS"));
//    }

    @Override
    public void lifecycleEvent(LifecycleEvent lifecycleEvent) {
        Boolean fipsMode = false;
        String fipsModeProperty = System.getProperty("org.bouncycastle.fips.approved_only");
        // Pef Flor fips-mode setup --enable
        // System.getProperty("fips-mode") returns "enable" if fips-mode is enabled - not sure
        if ("true".equalsIgnoreCase(fipsModeProperty)) {
            LOG.info("FIPS mode is enabled");
            fipsMode=true;
        } else {
            LOG.info("FIPS mode is disabled");
        }

        if (fipsMode) {
            //Get all providers configured in java.security file in JVM (jre\lib\security\java.security)
            Provider[] providers = Security.getProviders();
            LOG.info("Removing all providers configured in JVM (java.security file) ");
            if (providers != null && providers.length > 0) {
                for(int i = 0; i < providers.length; i++ ) {
                    // Don't remove Sun SSL provider - SunJSSE, we still are using SunJSSE but we can remove it if using JSSE provided by Bouncy Castle.
                    if (!providers[i].getName().equalsIgnoreCase("SunJSSE")) {
                        LOG.info("Removing provider: " +providers[i].getName() + " Info: " +providers[i].getInfo());
                        // Remove all providers
                        Security.removeProvider(providers[i].getName());
                    }
                }
            }

            Security.insertProviderAt(new BouncyCastleFipsProvider("C:HYBRID;ENABLE{All};"),1);
            // OR Security.insertProviderAt(new BouncyCastleFipsProvider(BC_FIPS_CONFIG),1);
        }
    }
}
