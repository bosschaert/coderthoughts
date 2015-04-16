package org.coderthoughts.srperf.impl;

import java.util.ArrayList;
import java.util.List;

import org.coderthoughts.srperf.api.Runner;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.framework.ServiceRegistration;

class SingleBundleSingletonRunner extends Thread implements Runner {
    private final BundleContext ctx;
    private final ServiceReference<String> ref;
    private long counter;
    private volatile boolean stop = false;

    private SingleBundleSingletonRunner(BundleContext context, ServiceReference<String> reference) {
        ctx = context;
        ref = reference;
    }

    @Override
    public void run() {
        while(!stop) {
            try {
                String svc = ctx.getService(ref);
                if (!"foo".equals(svc))
                    System.out.println("Bad Service!");
                else
                    counter++;
            } finally {
                ctx.ungetService(ref);
            }
        }
        System.out.println("Thread done (" + getClass().getSimpleName() + ")");
    }

    @Override
    public double getResult() {
        return counter / 1000000.0;
    }

    @Override
    public void stopRunner() {
        stop = true;
    }

    static List<Runner> startRunners(BundleContext ctx, int numThreads) {
        ServiceRegistration<String> reg = ctx.registerService(String.class, "foo", null);

        List<Runner> runners = new ArrayList<>();
        for (int i=0; i < numThreads; i++) {
            SingleBundleSingletonRunner r = new SingleBundleSingletonRunner(ctx, reg.getReference());
            runners.add(r);
            r.start();
        }
        return runners;
    }
}