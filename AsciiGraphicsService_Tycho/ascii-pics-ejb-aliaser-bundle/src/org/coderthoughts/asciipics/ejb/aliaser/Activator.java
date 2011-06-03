package org.coderthoughts.asciipics.ejb.aliaser;

import javax.naming.InitialContext;

import org.coderthoughts.asciipics.api.PictureService;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.framework.ServiceRegistration;

public class Activator implements BundleActivator {
    private static final String JNDI_NAME = "java:global/ascii-pics-provider-ejb-1.0-SNAPSHOT/AsciiPicEJB";

    private ServiceRegistration reg;

    @Override
    public void start(BundleContext context) throws Exception {
        InitialContext iniCtx = getInitialContext(context);
        Object ejbObj = iniCtx.lookup(JNDI_NAME);

        if (ejbObj instanceof PictureService == false) {
            throw new IllegalStateException("JNDI registration " + JNDI_NAME + " does not implement "
                    + PictureService.class.getName() + " interface: " + ejbObj);
        }

        reg = context.registerService(PictureService.class.getName(), ejbObj, null);
        System.out.println("Registered EJB object in OSGi Service registry");
    }

    private InitialContext getInitialContext(BundleContext context) {
        ServiceReference sref = context.getServiceReference(InitialContext.class.getName());
        InitialContext initContext = (InitialContext) context.getService(sref);
        return initContext;
    }

    @Override
    public void stop(BundleContext context) throws Exception {
        reg.unregister();
    }
}
