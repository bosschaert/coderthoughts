package org.coderthoughts.osgi.cloud.api.impl;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.UnknownHostException;
import java.util.Dictionary;
import java.util.Hashtable;
import java.util.Random;
import java.util.UUID;

import org.coderthoughts.osgi.cloud.api.OSGiFrameworkPublisher;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;

public class Activator implements BundleActivator {
    private static final String OSGI_FRAMEWORK_UUID = "org.osgi.framework.uuid";

    private OSGiFrameworkPublisherImpl publisher;
    private ServiceRegistration reg;

    public void start(BundleContext context) throws Exception {
        String uuid = context.getProperty(OSGI_FRAMEWORK_UUID);
        if (uuid == null) {
            uuid = UUID.randomUUID().toString();
            System.setProperty(OSGI_FRAMEWORK_UUID, uuid);
        }

        String host = getHostName();
        int port = getPort();

        Dictionary<String, Object> props = new Hashtable<String, Object>();
        props.put(OSGI_FRAMEWORK_UUID, uuid);
        props.put("org.osgi.framework.ip", "192.168.1." + new Random().nextInt(256));
        props.put("service.exported.interfaces", "*");

        // These properties here are to avoid port clashes when multiple instances are running
        // on a single machine... This is CXF-specific and it would be good if we can make it generic
        props.put("service.exported.configs", "org.apache.cxf.ws");
        // props.put("org.apache.cxf.ws.address", getAddress(host, port)); // old obsolete value
        props.put("endpoint.id", getAddress(host, port));

        publisher = new OSGiFrameworkPublisherImpl(context, props);
        reg = context.registerService(OSGiFrameworkPublisher.class.getName(), publisher, null);
    }

    public void stop(BundleContext context) throws Exception {
        publisher.removeRegistration();
        reg.unregister();
    }

    private static String getAddress(String host, int port) throws Exception {
        return "http://" + host + ":" + port + "/OSGiFramework";
    }

    private static String getHostName() {
        try {
            return InetAddress.getLocalHost().getCanonicalHostName();
        } catch (UnknownHostException e) {
            return "localhost";
        }
    }

    private static int getPort() throws IOException {
        return new ServerSocket(0).getLocalPort();
    }
}
