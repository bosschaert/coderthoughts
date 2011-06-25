package org.coderthoughts.demo.web.osgi;

import org.coderthoughts.demo.web.osgi.api.StockInfoService;
import org.coderthoughts.demo.web.osgi.api.StockQuote;
import org.jboss.msc.service.AbstractService;
import org.jboss.msc.service.ServiceActivator;
import org.jboss.msc.service.ServiceActivatorContext;
import org.jboss.msc.service.ServiceBuilder;
import org.jboss.msc.service.ServiceController.Mode;
import org.jboss.msc.service.ServiceName;
import org.jboss.msc.service.ServiceRegistryException;
import org.jboss.msc.service.ServiceTarget;
import org.jboss.msc.service.StartContext;
import org.jboss.msc.service.StartException;
import org.jboss.msc.service.StopContext;
import org.jboss.msc.value.InjectedValue;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;

/**
 * This MSC service provides access to an OSGi Service.
 */
public class OSGiServiceConnector implements ServiceActivator {
    // The MSC service name of the Bundle Context of the OSGi System Bundle
    private static final ServiceName SYSTEM_BUNDLE_CONTEXT = ServiceName.of("jbosgi").append("SystemContext");
    private static StockInfoService invoker;

    public void activate(ServiceActivatorContext context) throws ServiceRegistryException {
        System.out.println("*** Activating MSC service: " + getClass().getName());

        ServiceTarget serviceTarget = context.getServiceTarget();
        OSGiServiceInvoker.addService(serviceTarget);
    }

    public static StockInfoService getInvoker() {
        return invoker;
    }

    private static class OSGiServiceInvoker extends AbstractService<Void> implements StockInfoService {
        private InjectedValue<BundleContext> injectedBundleContext = new InjectedValue<BundleContext>();

        public static void addService(ServiceTarget serviceTarget) {
            OSGiServiceInvoker invokerService = new OSGiServiceInvoker();
            ServiceName serviceName = ServiceName.of("TestService");
            ServiceBuilder<?> serviceBuilder = serviceTarget.addService(serviceName, invokerService);
            serviceBuilder.addDependency(SYSTEM_BUNDLE_CONTEXT, BundleContext.class, invokerService.injectedBundleContext);
            serviceBuilder.setInitialMode(Mode.ACTIVE);
            serviceBuilder.install();
        }

        @Override
        public void start(StartContext context) throws StartException {
            // This service is ready
            invoker = this;
        }

        public StockQuote getStockQuote(String key) {
            BundleContext bundleContext = injectedBundleContext.getValue();
            ServiceReference reference = bundleContext.getServiceReference(StockInfoService.class.getName());
            if (reference != null) {
                try {
                    StockInfoService ois = (StockInfoService) bundleContext.getService(reference);
                    return ois.getStockQuote(key);
                } finally {
                    bundleContext.ungetService(reference);
                }
            }
            return null;
        }

        @Override
        public void stop(StopContext context) {
            invoker = null;
        }
    }
}
