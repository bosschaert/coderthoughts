package org.coderthoughts.osgi.cloud.api.impl;

import java.util.Dictionary;

import org.coderthoughts.osgi.cloud.api.OSGiFramework;
import org.coderthoughts.osgi.cloud.api.OSGiFrameworkPublisher;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;

// TODO this class works around some bugs in CXF-DOSGi. Once these bugs are fixed this class can be
// simplified.
// The bugs in CXF-DOSGi are:
// * Modifying an existing service registration has no effect with discovery
// * unregistering and then re-registering has no effect with discovery
public class OSGiFrameworkPublisherImpl implements OSGiFrameworkPublisher {
    private final BundleContext bundleContext;
    private ServiceRegistration registration;
    private final Dictionary<String, Object> properties;

    public OSGiFrameworkPublisherImpl(BundleContext context, Dictionary<String, Object> props) {
        bundleContext = context;
        properties = props;

        // Should be able to do this right here, but apparently the re-registration doesn't work in CXF
        // syncRegistration();
    }

    private void syncRegistration() {
        removeRegistration();
        registration = bundleContext.registerService(OSGiFramework.class.getName(),
                new OSGiFramework() {}, properties);
        System.out.println("*** Registered with: " + properties);
    }

    void removeRegistration() {
        if (registration != null)
            registration.unregister();
    }

    public String getProperty(String key) {
        Object val = properties.get(key);
        if (val != null)
            return val.toString();

        return null;
    }

    public void setProperty(String key, String value) {
        if (value == null)
            properties.remove(key);
        else
            properties.put(key, value);

        // This should work but apparently it doesn't:
        //   registration.setProperties(properties);
        // so we're using a de-reg and re-reg instead...
        // There is a big bug her in the disco, currently it only works the first time you set a property
        syncRegistration();
    }
}
