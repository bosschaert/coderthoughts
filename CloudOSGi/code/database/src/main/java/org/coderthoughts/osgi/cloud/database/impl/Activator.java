package org.coderthoughts.osgi.cloud.database.impl;

import org.coderthoughts.mycloudapp.api.AppConstants;
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
                return svc;
            }
        };
        st.open();
    }

    public void stop(BundleContext context) throws Exception {
        st.close();
    }
}
