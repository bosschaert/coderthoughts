package org.coderthoughts.demo.cdiosgi.consumer.impl;

import org.coderthoughts.demo.cdiosgi.api.WarBean;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.util.tracker.ServiceTracker;

public class Activator implements BundleActivator {
    private ServiceTracker tracker;

    public void start(BundleContext context) throws Exception {
        System.out.println("*** osgi-cdi-demo-consumer-bundle started.");

        tracker = new ServiceTracker(context, WarBean.class.getName(), null) {
            public Object addingService(ServiceReference reference) {
                Object obj = super.addingService(reference);
                if (obj instanceof WarBean) {
                    String result = ((WarBean) obj).getValue();
                    System.out.println("*** Result from EE bean-injected service: " + result);
                }
                return obj;
            }
        };
        tracker.open();
    }

    public void stop(BundleContext context) throws Exception {
        tracker.close();
    }
}
