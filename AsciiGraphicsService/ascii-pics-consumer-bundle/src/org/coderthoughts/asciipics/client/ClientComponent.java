package org.coderthoughts.asciipics.client;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.coderthoughts.asciipics.api.PictureService;

public class ClientComponent {
    List<PictureService> services = new ArrayList<PictureService>();
    private volatile boolean keepRunning;

    public void activate() {
        keepRunning = true;
        new Thread(new DilbertPrinter()).start();
    }

    public void deactivate() {
        keepRunning = false;
    }

    public void addPictureService(PictureService ps) {
        synchronized (services) {
            services.add(ps);
        }
    }

    public void removePictureService(PictureService ps) {
        synchronized (services) {
            services.remove(ps);
        }
    }

    private class DilbertPrinter implements Runnable {
        @Override
        public void run() {
            while (keepRunning) {
                PictureService ps = null;
                synchronized (services) {
                    if (services.size() > 0) {
                        ps = services.get(0);
                    }
                }
                if (ps != null) {
                    System.out.println();
                    System.out.println(ps.getPic("Dilbert"));
                }
                try {
                    TimeUnit.SECONDS.sleep(5);
                } catch (InterruptedException e) {
                    // oh well
                }
            }
        }
    }
}
