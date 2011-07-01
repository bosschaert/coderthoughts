package org.coderthoughts.osgi.cloud.database.impl;

import org.coderthoughts.mycloudapp.api.AppConstants;
import org.coderthoughts.mycloudapp.common.Window;
import org.coderthoughts.osgi.cloud.api.OSGiFrameworkPublisher;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.util.tracker.ServiceTracker;

public class Activator implements BundleActivator {
    private ServiceTracker st;

    public void start(BundleContext context) throws Exception {
        st = new ServiceTracker(context, OSGiFrameworkPublisher.class.getName(), null) {
            @Override
            public Object addingService(ServiceReference reference) {
                Object svc = super.addingService(reference);
                if (svc instanceof OSGiFrameworkPublisher) {
                    ((OSGiFrameworkPublisher) svc).setProperty(AppConstants.ROLE, AppConstants.DATABASE);
                    System.out.println("*** Registered as Database");
                }

                String ip = referenceIP(reference);
                new Window("Database on " + ip);
                return svc;
            }
        };
        st.open();
    }

    private String referenceIP(ServiceReference reference) {
        Object prop = reference.getProperty("org.osgi.framework.ip");
        if (prop != null)
            return prop.toString();
        return null;
    }


    public void stop(BundleContext context) throws Exception {
        st.close();
    }
}
