package org.coderthoughts.jndiosgi.impl;

import javax.naming.InitialContext;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

public class Activator implements BundleActivator {

    public void start(BundleContext context) throws Exception {
        System.out.println("@*********** hi there");

//        for (Bundle b : context.getBundles()) {
//            System.out.println("Bundle: " + b.getSymbolicName());
//        }

        try {
            InitialContext ctx = new InitialContext();
//            System.out.println("Initial Context: " + ctx);
            Object ut = ctx.lookup("UserTransaction");
            System.out.println("Found ut: " + ut);
            Object p = ctx.lookup("jdbc/db");
            System.out.println("Found p: " + p);
//            Object bc = ctx.lookup("osgi:framework/bundleContext");
//            System.out.println("Found: " + bc);
//            Object s = ctx.lookup("osgi:service/javax.transaction.UserTransaction/(component.name=UserTransaction)");
//            System.out.println("Service: " + s);
//            Object s2 = ctx.lookup("osgi:service/org.osgi.service.packageadmin.PackageAdmin");
//            System.out.println("Service: " + s2);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
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