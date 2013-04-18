package org.coderthoughts.subsystems.gogo;

import java.io.IOException;
import java.net.URL;
import java.util.Dictionary;
import java.util.Hashtable;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.InvalidSyntaxException;
import org.osgi.framework.ServiceReference;
import org.osgi.service.subsystem.Subsystem;

public class Activator implements BundleActivator {
    private BundleContext bundleContext;

    public void start(BundleContext context) throws Exception {
        bundleContext = context;

		Dictionary<String, Object> props = new Hashtable<String, Object>();
		props.put("osgi.command.function", new String [] {"install", "uninstall", "start", "stop", "list"});
		props.put("osgi.command.scope", "subsystem");
		context.registerService(getClass().getName(), this, props);
	}

	public void install(String url) throws IOException {
	    Subsystem rootSubsystem = getSubsystem(0);
		System.out.println("Installing subsystem: " + url);
		Subsystem s = rootSubsystem.install(url, new URL(url).openStream());
		System.out.println("Subsystem succesfully installed: " + s.getSymbolicName() + "; id: " + s.getSubsystemId());
	}

    public void uninstall(long id) {
        getSubsystem(id).uninstall();
    }

    public void start(long id) {
        getSubsystem(id).start();
    }

    public void stop(long id) {
        getSubsystem(id).stop();
    }

    public void list() throws InvalidSyntaxException {
        for (ServiceReference<Subsystem> ref : bundleContext.getServiceReferences(Subsystem.class, null)) {
            Subsystem s = bundleContext.getService(ref);
            if (s != null) {
                System.out.printf("%d\t%s\t%s\n", s.getSubsystemId(), s.getState(), s.getSymbolicName());
            }
        }
    }

    private Subsystem getSubsystem(long id) {
        try {
            for (ServiceReference<Subsystem> ref : bundleContext.getServiceReferences(Subsystem.class, "(subsystem.id=" + id + ")")) {
                Subsystem svc = bundleContext.getService(ref);
                if (svc != null)
                    return svc;
            }
        } catch (InvalidSyntaxException e) {
            throw new RuntimeException(e);
        }
        throw new RuntimeException("Unable to find subsystem " + id);
    }

    public void stop(BundleContext context) throws Exception {}
}
