package org.coderthoughts.jndiosgi.impl;

import javax.naming.InitialContext;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

public class Activator implements BundleActivator {

    public void start(BundleContext context) throws Exception {
        System.out.println("*********** hi there");

        for (Bundle b : context.getBundles()) {
            System.out.println("Bundle: " + b.getSymbolicName());
        }

        InitialContext ctx = new InitialContext();
//        System.out.println("$$: " + ctx.lookup("java:comp/env/jdbc/DefaultDatasource"));
//        NamingEnumeration<NameClassPair> list = ctx.list("java:comp/env/");
//        while (list.hasMore()) {
//          System.out.println("##: " + list.next().getName());
//        }

        /*
        ServiceReference<?>[] refs = context.getServiceReferences((String) null, null);
        for (ServiceReference ref : refs) {
            System.out.println("**** Service:");
            System.out.println(ref);
        }
        */

        /*
        ServiceTracker st = new ServiceTracker(context, JNDIContextManager.class.getName(), null) {
            @Override
            public Object addingService(ServiceReference reference) {
                Object svc = super.addingService(reference);
                System.out.println("#### Found: " + svc);
                return svc;
            }
        };
        st.open();
        */
    }

    public void stop(BundleContext context) throws Exception {
    }
}