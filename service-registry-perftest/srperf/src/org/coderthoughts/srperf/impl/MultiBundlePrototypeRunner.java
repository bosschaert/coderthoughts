package org.coderthoughts.srperf.impl;

import java.util.ArrayList;
import java.util.List;

import org.coderthoughts.srperf.api.Runner;
import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.BundleException;
import org.osgi.framework.PrototypeServiceFactory;
import org.osgi.framework.ServiceObjects;
import org.osgi.framework.ServiceReference;
import org.osgi.framework.ServiceRegistration;

class MultiBundlePrototypeRunner extends Thread implements Runner {
    private final BundleContext ctx;
    private final ServiceReference<String> ref;
    private long counter;
    private volatile boolean stop = false;

    private MultiBundlePrototypeRunner(BundleContext context, ServiceReference<String> reference) {
        ctx = context;
        ref = reference;
    }

    @Override
    public void run() {
        while(!stop) {
            ServiceObjects<String> serviceObjects = ctx.getServiceObjects(ref);
            try {
                String svc = serviceObjects.getService();
                if (!"toast".equals(svc))
                    System.out.println("Bad Service!");
                else
                    counter++;
            } finally {
                ctx.ungetService(ref);
            }
        }

        String bsn = ctx.getBundle().getSymbolicName();
        try {
            ctx.getBundle().uninstall();
        } catch (BundleException e) {
            e.printStackTrace();
        }
        System.out.println("Thread done (" + getClass().getSimpleName() + "), bundle uninstalled: " + bsn);
    }

    @Override
    public double getResult() {
        return counter / 10000.0;
    }

    @Override
    public void stopRunner() {
        stop = true;
    }

    static List<Runner> startRunners(BundleContext ctx, int numThreads) {
        ServiceRegistration<String> reg = ctx.registerService(String.class, new MyPSF(), null);

        List<Bundle> bundles = new ArrayList<>();
        for (int i=0; i < numThreads; i++) {
            bundles.add(MockBundleTools.startMockBundle(ctx, "toastbundle_" + i));
        }

        List<Runner> runners = new ArrayList<>();
        for (Bundle b : bundles) {
            MultiBundlePrototypeRunner r = new MultiBundlePrototypeRunner(b.getBundleContext(), reg.getReference());
            runners.add(r);
            r.start();
        }
        return runners;
    }

    private static class MyPSF implements PrototypeServiceFactory<String> {
        @Override
        public String getService(Bundle bundle, ServiceRegistration<String> registration) {
            return "toast";
        }

        @Override
        public void ungetService(Bundle bundle, ServiceRegistration<String> registration, String service) {
        }
    }
}