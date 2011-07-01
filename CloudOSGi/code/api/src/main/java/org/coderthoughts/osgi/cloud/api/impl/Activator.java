package org.coderthoughts.osgi.cloud.api.impl;

import java.util.Dictionary;
import java.util.Hashtable;
import java.util.Random;
import java.util.UUID;

import org.coderthoughts.osgi.cloud.api.OSGiFramework;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;

public class Activator implements BundleActivator {
    private static final String OSGI_FRAMEWORK_UUID = "org.osgi.framework.uuid";

    private ServiceRegistration reg;

    public void start(BundleContext context) throws Exception {
        String uuid = context.getProperty(OSGI_FRAMEWORK_UUID);
        if (uuid == null) {
            uuid = UUID.randomUUID().toString();
            System.setProperty(OSGI_FRAMEWORK_UUID, uuid);
        }

        OSGiFramework fw = new OSGiFramework() {};

        Dictionary<String, Object> props = new Hashtable<String, Object>();
        props.put(OSGI_FRAMEWORK_UUID, uuid);
        props.put("org.osgi.framework.ip", "192.168.1." + new Random().nextInt(256));
        props.put("service.exported.interfaces", "*");

        reg = context.registerService(OSGiFramework.class.getName(), fw, props);
    }

    public void stop(BundleContext context) throws Exception {
        reg.unregister();
    }
}
