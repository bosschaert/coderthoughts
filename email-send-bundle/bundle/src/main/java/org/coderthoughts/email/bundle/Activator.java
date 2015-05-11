package org.coderthoughts.email.bundle;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

public class Activator implements BundleActivator {

    public void start(BundleContext context) throws Exception {
        System.out.println("*********** Email Bundle Started");

//        HtmlEmail email = new HtmlEmail();
//        email.setCharset("UTF-8");
//        email.setHtmlMsg("<h1>Hello</h1>");
//        email.setSubject("Test email");
//        email.addTo("bosschae@adobe.com");

        Class<?> cls = getClass().getClassLoader().loadClass("javax.activation.DataHandler");
        System.out.println("Data Handler: " + cls);
        System.out.println("Data Handler ClassLoader: " + cls.getClassLoader());


//        ServiceTracker st = new ServiceTracker(context, "org.osgi.service.http.HttpService", null) {
//            @Override
//            public Object addingService(ServiceReference reference) {
//                Object svc = super.addingService(reference);
//                System.out.println("Found Http Service: " + svc);
//                return svc;
//            }
//        };
//        st.open();
    }

    public void stop(BundleContext context) throws Exception {
        System.out.println("*********** Email Bundle Stopped");
    }
}