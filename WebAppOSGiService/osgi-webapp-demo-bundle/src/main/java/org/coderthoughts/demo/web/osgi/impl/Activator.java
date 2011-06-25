package org.coderthoughts.demo.web.osgi.impl;

import java.util.Dictionary;
import java.util.Hashtable;

import org.coderthoughts.demo.web.osgi.api.StockInfoService;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;

public class Activator implements BundleActivator {
	private ServiceRegistration reg;

    public void start(BundleContext context) throws Exception {
	    StockInfoService ois = new StockInfoServiceImpl();
	    Dictionary props = new Hashtable();
	    props.put("delay", "" + StockInfoServiceImpl.DELAY);
	    reg = context.registerService(StockInfoService.class.getName(), ois, props);
	    System.out.println("*** Registered OSGi Service");
	}

	public void stop(BundleContext context) throws Exception {
	    reg.unregister();
	}
}
