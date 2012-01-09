package org.coderthoughts.msc;

import org.jboss.msc.service.ServiceActivator;
import org.jboss.msc.service.ServiceActivatorContext;
import org.jboss.msc.service.ServiceRegistryException;

public class MSCServiceActivator implements ServiceActivator {
    public void activate(ServiceActivatorContext context) throws ServiceRegistryException {
        System.out.println("*** Activating MSC service: " + getClass().getName());
    }
}
