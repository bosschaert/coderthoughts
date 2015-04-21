package org.coderthoughts.basic.bundle1;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.util.tracker.ServiceTracker;

public class Activator implements BundleActivator {

    public void start(BundleContext context) throws Exception {
        System.out.println("@*********** hi there");

        ServiceTracker st = new ServiceTracker(context, "org.osgi.service.http.HttpService", null) {
            @Override
            public Object addingService(ServiceReference reference) {
                Object svc = super.addingService(reference);
                System.out.println("Found Http Service: " + svc);
                return svc;
            }
        };
        st.open();
    }

    public void stop(BundleContext context) throws Exception {
    }
}