package org.coderthoughts.bundle1;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

public class Activator implements BundleActivator {
    @Override
    public void start(BundleContext context) throws Exception {
        System.out.println("Started: " + context.getBundle().getSymbolicName());
    }

    @Override
    public void stop(BundleContext context) throws Exception {
        System.out.println("Stopped: " + context.getBundle().getSymbolicName());
    }
}
