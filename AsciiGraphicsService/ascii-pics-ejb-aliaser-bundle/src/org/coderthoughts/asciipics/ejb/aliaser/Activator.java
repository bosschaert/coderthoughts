package org.coderthoughts.asciipics.ejb.aliaser;

import java.lang.reflect.Method;

import javax.naming.InitialContext;

import org.coderthoughts.asciipics.api.PictureService;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.framework.ServiceRegistration;

public class Activator implements BundleActivator {
    private ServiceRegistration reg;

    @Override
    public void start(BundleContext context) throws Exception {
        // TODO this should happen in a deployment processor
        // System.out.println("Aliaser started: " + context);
        // System.out.println("*** Initial Context" + getInitialContext(context));
        InitialContext iniCtx = getInitialContext(context);
        // final Object ejbObj = iniCtx
        // .lookup("java:global/AsciiPicsEJB/AsciiPicEJB!org.coderthoughts.asciipics.ejb.PictureServiceRemote");
        final Object ejbObj = iniCtx
                .lookup("java:global/AsciiPicsEJB/AsciiPicEJB!org.coderthoughts.asciipics.ejb.PictureServiceLocal");

        // System.out.println("**** " + ejbObj);
        // ClassLoader ctxLoader = Thread.currentThread().getContextClassLoader();
        // System.out.println("~~~~ " + ejbObj.getClass().getDeclaredMethod("getPic", String.class).invoke(ejbObj, "xyz"));
        ejbObj.getClass().getDeclaredMethod("getPic", String.class).invoke(ejbObj, "xyz");

        final Method ejbMethod = ejbObj.getClass().getDeclaredMethod("getPic", String.class);

        PictureService service = new PictureService() {
            @Override
            public String getPic(String name) {
                try {
                    Object s = ejbMethod.invoke(ejbObj, name);
                    return s != null ? s.toString() : "";
                } catch (Exception e) {
                    return e.getMessage();
                }
            }
        };
        reg = context.registerService(PictureService.class.getName(), service, null);
        System.out.println("Registered Aliaser");
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
