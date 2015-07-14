package org.coderthoughts.async;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.service.async.Async;
import org.osgi.util.promise.Promise;
import org.osgi.util.promise.Promises;
import org.osgi.util.tracker.ServiceTracker;

public class Activator implements BundleActivator {
    BundleContext bundleContext;
    private ServiceTracker<TimeConsumingService, TimeConsumingService> st;

    @Override
    public void start(BundleContext context) throws Exception {
        bundleContext = context;
        st = new ServiceTracker<TimeConsumingService, TimeConsumingService>(
                context, TimeConsumingService.class, null) {
            @Override
            public TimeConsumingService addingService(ServiceReference<TimeConsumingService> reference) {
                TimeConsumingService tcs = super.addingService(reference);
                invokePlain(tcs);
                invokeAsync(reference);
                return tcs;
            }
        };
        st.open();

        context.registerService(TimeConsumingService.class, new TimeConsumingServiceImpl(), null);
    }

    @Override
    public void stop(BundleContext context) throws Exception {
        st.close();
    }

    private void invokePlain(TimeConsumingService tcs) {
        System.out.println("Invoking Big Task Synchronously...");
        System.out.println("Big Task returned: " + tcs.bigTask(1));
    }

    protected void invokeAsync(ServiceReference<TimeConsumingService> reference) {
        ServiceReference<Async> asyncRef = bundleContext.getServiceReference(Async.class);
        if (asyncRef == null) {
            System.out.println("Async Service Not Found");
            return;
        }

        Async async = bundleContext.getService(asyncRef);
        if (async == null) {
            System.out.println("Async Service Not Found");
            return;
        }

        TimeConsumingService mediated = async.mediate(reference, TimeConsumingService.class);
        System.out.println("Invoking Big Task Asynchronously...");
        async.call(mediated.bigTask(1))
            .then(p -> bigTaskFinished(p.getValue()));
        System.out.println("Big Task submitted");
    }

    private Promise<Void> bigTaskFinished(int value) {
        System.out.println("Big task finished: " + value);
        return Promises.resolved(null);
    }
}

