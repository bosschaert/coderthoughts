package org.coderthoughts.osgi.cloud.frontend.impl;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import org.coderthoughts.mycloudapp.api.AppConstants;
import org.coderthoughts.mycloudapp.common.Window;
import org.coderthoughts.osgi.cloud.api.OSGiFramework;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.Constants;
import org.osgi.framework.Filter;
import org.osgi.framework.FrameworkUtil;
import org.osgi.framework.InvalidSyntaxException;
import org.osgi.framework.ServiceReference;
import org.osgi.util.tracker.ServiceTracker;
import org.osgi.util.tracker.ServiceTrackerCustomizer;

public class Activator implements BundleActivator {
    private ServiceTracker st;
    private final Window window = new Window("Web Frontend");
    private final List<ServiceReference> databases = new CopyOnWriteArrayList<ServiceReference>();

    public void start(BundleContext context) throws Exception {
        window.addText("Looking for a Database...");

        OSGiFrameworkTrackerCustomizer customizer = new OSGiFrameworkTrackerCustomizer(AppConstants.DATABASE, databases) {
            public Object addingService(ServiceReference reference) {
                window.addText("Database appeared on: " + referenceIP(reference));
                return super.addingService(reference);
            }

            public void removedService(ServiceReference reference, Object service) {
                super.removedService(reference, service);
                window.addText("Database on: " + referenceIP(reference) + " disappeared.");
            }
        };
        st = new ServiceTracker(context, customizer.getFilter(), customizer);
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

    private static class OSGiFrameworkTrackerCustomizer implements ServiceTrackerCustomizer {
        private final String role;
        private final List<ServiceReference> references;

        public OSGiFrameworkTrackerCustomizer(String role, List<ServiceReference> references) {
            this.role = role;
            this.references = references;
        }

        private Filter getFilter() throws InvalidSyntaxException {
            return FrameworkUtil.createFilter("(&(" + Constants.OBJECTCLASS + "=" + OSGiFramework.class.getName() + ")" +
            		"(" + AppConstants.ROLE + "=" + role + "))");
        }

        public Object addingService(ServiceReference reference) {
            System.out.println("Added: " + formatServiceReference(reference));
            references.add(reference);
            return reference;
        }

        public void modifiedService(ServiceReference reference, Object service) {
            System.out.println("Modified: " + formatServiceReference(reference));
        }

        public void removedService(ServiceReference reference, Object service) {
            System.out.println("Removed: " + formatServiceReference(reference));
            references.remove(reference);
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
