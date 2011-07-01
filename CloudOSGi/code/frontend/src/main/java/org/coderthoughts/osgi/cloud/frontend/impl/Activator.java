package org.coderthoughts.osgi.cloud.frontend.impl;

import java.util.Arrays;

import org.coderthoughts.osgi.cloud.api.OSGiFramework;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.Constants;
import org.osgi.framework.ServiceReference;
import org.osgi.util.tracker.ServiceTracker;
import org.osgi.util.tracker.ServiceTrackerCustomizer;

public class Activator implements BundleActivator {
    private ServiceTracker st;

    public void start(BundleContext context) throws Exception {
        System.out.println("This is the front-end Bundle");

        st = new ServiceTracker(context, OSGiFramework.class.getName(), new OSGiFrameworkTrackerCustomizer());
        st.open();
    }

    public void stop(BundleContext context) throws Exception {
        st.close();
    }

    private static class OSGiFrameworkTrackerCustomizer implements ServiceTrackerCustomizer {
        public Object addingService(ServiceReference reference) {
            System.out.println("Added: " + formatServiceReference(reference));
            return reference;
        }

        public void modifiedService(ServiceReference reference, Object service) {
            System.out.println("Modified: " + formatServiceReference(reference));
        }

        public void removedService(ServiceReference reference, Object service) {
            System.out.println("Removed: " + formatServiceReference(reference));
        }
    }

    public static String formatServiceReference(ServiceReference reference) {
        Object objClass = reference.getProperty(Constants.OBJECTCLASS);
        StringBuilder sb = new StringBuilder("\n\n\n" + formatStringPlus(objClass) + "\n");

        String[] keys = reference.getPropertyKeys();
        Arrays.sort(keys);
        for (String key : keys) {
            if (Constants.OBJECTCLASS.equals(key))
                continue;

            sb.append("  " + key + ":" + formatStringPlus(reference.getProperty(key)) + "\n");
        }

        return sb.toString();
    }

    private static String formatStringPlus(Object objClass) {
        if (objClass instanceof String[]) {
            objClass = Arrays.asList((String [])objClass);
        }
        return objClass.toString();
    }
}
