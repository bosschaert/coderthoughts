package org.coderthoughts.demo.cdiosgi.provider.impl;

import org.coderthoughts.demo.cdiosgi.api.OSGiPropertyProvider;
import org.osgi.framework.BundleContext;

class OSGiPropertyProviderImpl implements OSGiPropertyProvider {
    private final BundleContext bundleContext;

    public OSGiPropertyProviderImpl(BundleContext bc) {
        bundleContext = bc;
    }

    public String getProperty(String name) {
        return bundleContext.getProperty(name);
    }
}
