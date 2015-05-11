package org.coderthoughts.srperf.impl;

import java.util.ArrayList;
import java.util.List;

import org.coderthoughts.srperf.api.Runner;
import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.PrototypeServiceFactory;
import org.osgi.framework.ServiceObjects;
import org.osgi.framework.ServiceReference;
import org.osgi.framework.ServiceRegistration;

// Registers a prototype service factory that sleeps 10ms in the getService() method.
// Then access it in multiple threads from a single bundle via ServiceObjects and count
// the number of times until stopped.
class SingleBundlePrototypeRunner extends Thread implements Runner {
    private final BundleContext ctx;
    private final ServiceReference<String> ref;
    private long counter;
    private volatile boolean stop = false;

    private SingleBundlePrototypeRunner(BundleContext context, ServiceReference<String> reference) {
        ctx = context;
        ref = reference;
    }

    @Override
    public void run() {
        while(!stop) {
            ServiceObjects<String> serviceObjects = ctx.getServiceObjects(ref);
            try {
                String svc = serviceObjects.getService();
                if (!"bar".equals(svc))
                    System.out.println("Bad Service!");
                else
                    counter++;
//                serviceObjects.ungetService(svc); // TODO HUH????? java.lang.IllegalArgumentException: The service parameter was not provided by this object
            } finally {
                ctx.ungetService(ref);
            }
        }
        System.out.println("Thread done (" + getClass().getSimpleName() + ")");
    }

    @Override
    public double getResult() {
        return counter / 40.0;
    }

    @Override
    public void stopRunner() {
        stop = true;
    }

    static List<Runner> startRunners(BundleContext ctx, int numThreads) {
        ServiceRegistration<String> reg = ctx.registerService(String.class, new PSF(), null);

        List<Runner> runners = new ArrayList<>();
        for (int i=0; i < numThreads; i++) {
            SingleBundlePrototypeRunner r = new SingleBundlePrototypeRunner(ctx, reg.getReference());
            runners.add(r);
            r.start();
        }
        return runners;
    }

    private static class PSF implements PrototypeServiceFactory<String> {
        @Override
        public String getService(Bundle bundle, ServiceRegistration<String> registration) {
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
            }
            return "bar";
        }

        @Override
        public void ungetService(Bundle bundle, ServiceRegistration<String> registration, String service) {
        }
    }
}