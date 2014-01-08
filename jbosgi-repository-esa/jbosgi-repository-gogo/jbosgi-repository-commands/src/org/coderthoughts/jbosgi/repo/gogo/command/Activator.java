package org.coderthoughts.jbosgi.repo.gogo.command;

import java.net.URL;
import java.util.Dictionary;
import java.util.Hashtable;

import org.jboss.osgi.repository.RepositoryReader;
import org.jboss.osgi.repository.RepositoryStorage;
import org.jboss.osgi.repository.RepositoryXMLReader;
import org.jboss.osgi.repository.XPersistentRepository;
import org.jboss.osgi.resolver.XResource;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.service.repository.Repository;
import org.osgi.util.tracker.ServiceTracker;

public class Activator implements BundleActivator {
    private ServiceTracker<Repository, Repository> repositoryTracker;

    @Override
    public void start(BundleContext context) throws Exception {
        Dictionary<String, Object> props = new Hashtable<String, Object>();
        props.put("osgi.command.function", new String[] { "addxml" });
        props.put("osgi.command.scope", "jbr");
        context.registerService(getClass().getName(), this, props);

        repositoryTracker = new ServiceTracker<Repository, Repository>(context, Repository.class.getName(), null);
        /*{
            @Override
            public Object addingService(ServiceReference reference) {
                Object svc = super.addingService(reference);
                if (svc instanceof XPersistentRepository) {
                    System.out.println("*** Found:" + ((XPersistentRepository) svc).getRepositoryStorage());
                }
                return svc;
            }
        };*/
        repositoryTracker.open();
    }

    public void addxml(String url) throws Exception {
        System.out.println("Adding repository.xml to JBoss OSGi Repository: " + url);

        XPersistentRepository xpr = null;
        for (Object svc : repositoryTracker.getServices()) {
            if (svc instanceof XPersistentRepository) {
                xpr = (XPersistentRepository) svc;
                break;
            }
        }
        if (xpr == null) {
            System.out.println("JBoss OSGi Repository Service not found");
            return;
        }

        RepositoryStorage rs = xpr.getRepositoryStorage();
        RepositoryReader reader = RepositoryXMLReader.create(new URL(url).openStream());
        XResource resource = reader.nextResource();
        while (resource != null) {
            System.out.println("  Adding resource: " + resource.getIdentityCapability().getSymbolicName());
            try {
                rs.addResource(resource);
            } catch (Exception e) {
                System.out.println("Resource not added: " + resource.getIdentityCapability().getSymbolicName() + " reason: " + e.getMessage());
            }
            resource = reader.nextResource();
        }
        System.out.println("Finished adding repository.xml");
    }

    @Override
    public void stop(BundleContext context) throws Exception {
        repositoryTracker.close();
    }
}
