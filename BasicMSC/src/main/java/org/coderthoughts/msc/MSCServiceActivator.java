package org.coderthoughts.msc;

import java.util.Hashtable;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NameClassPair;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;

import org.jboss.msc.service.ServiceActivator;
import org.jboss.msc.service.ServiceActivatorContext;
import org.jboss.msc.service.ServiceRegistryException;

public class MSCServiceActivator implements ServiceActivator {
    public void activate(ServiceActivatorContext context) throws ServiceRegistryException {
        System.out.println("*** Activating MSC service: " + getClass().getName());

        try {
          Hashtable env = new Hashtable();
          env.put(Context.INITIAL_CONTEXT_FACTORY, "org.jboss.as.naming.InitialContextFactory");
          InitialContext ctx = new InitialContext(env);
          System.out.println("@@@ CTX:" + ctx);
          System.out.println("###" + ctx.getEnvironment());

          NamingEnumeration<NameClassPair> res = ctx.list("");
          while(res.hasMore()) {
            System.out.println("@@@" + res.next());
          }
          res.close();
        } catch (NamingException e) {
          e.printStackTrace();
        }
    }
}
