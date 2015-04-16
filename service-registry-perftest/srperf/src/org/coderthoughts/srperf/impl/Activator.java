package org.coderthoughts.srperf.impl;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

public class Activator implements BundleActivator {
    private static final int DURATION_MS = 10000;
    private static final int NUM_THREADS = 10;

    @Override
    public void start(BundleContext context) throws Exception {
        System.out.println("Starting test");

        Stopper stopper = new Stopper(SingleBundleSingletonRunner.startRunners(context, NUM_THREADS), DURATION_MS);
        stopper.start();
        stopper.join();

        Thread.sleep(1000);

        Stopper stopper1 = new Stopper(SingleBundlePrototypeRunner.startRunners(context, NUM_THREADS), DURATION_MS);
        stopper1.start();
        stopper1.join();

        Thread.sleep(1000);

        Stopper stopper2 = new Stopper(MultiBundleSingletonRunner.startRunners(context, NUM_THREADS), DURATION_MS);
        stopper2.start();
        stopper2.join();

        Thread.sleep(1000);

        Stopper stopper3 = new Stopper(MultiBundlePrototypeRunner.startRunners(context, NUM_THREADS), DURATION_MS);
        stopper3.start();
        stopper3.join();

        new ResultCollector(stopper, stopper1, stopper2, stopper3).start();
    }


    @Override
    public void stop(BundleContext context) throws Exception {
    }
}
