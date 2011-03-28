package org.coderthoughts.asciipics;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

public class Activator implements BundleActivator {
    @Override
	public void start(BundleContext context) throws Exception {
		System.out.println("Hello World!! Hahaha");
	}

    @Override
	public void stop(BundleContext context) throws Exception {
		System.out.println("Goodbye World!!");
	}
}
