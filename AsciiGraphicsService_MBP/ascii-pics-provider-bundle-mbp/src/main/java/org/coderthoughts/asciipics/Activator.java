package org.coderthoughts.asciipics;

import org.coderthoughts.asciipics.api.PictureService;
import org.coderthoughts.asciipics.impl.PictureServiceImpl;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;

public class Activator implements BundleActivator {
    private ServiceRegistration reg;

    @Override
    public synchronized void start(BundleContext context) throws Exception {
        PictureService ps = new PictureServiceImpl();
        reg = context.registerService(PictureService.class.getName(), ps, null);
	}

    @Override
    public synchronized void stop(BundleContext context) throws Exception {
        reg.unregister();
	}
}
