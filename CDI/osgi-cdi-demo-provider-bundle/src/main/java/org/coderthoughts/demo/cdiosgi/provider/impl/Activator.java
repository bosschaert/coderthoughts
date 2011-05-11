package org.coderthoughts.demo.cdiosgi.provider.impl;

import org.coderthoughts.demo.cdiosgi.api.OSGiPropertyProvider;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;

public class Activator implements BundleActivator {
    private ServiceRegistration reg;

    public void start(BundleContext context) throws Exception {
        OSGiPropertyProvider pp = new OSGiPropertyProviderImpl(context);
        reg = context.registerService(OSGiPropertyProvider.class.getName(), pp, null);
    }

    public void stop(BundleContext context) throws Exception {
        reg.unregister();
    }
}
