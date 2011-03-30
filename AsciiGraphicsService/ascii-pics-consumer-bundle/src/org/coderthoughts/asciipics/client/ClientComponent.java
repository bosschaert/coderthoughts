package org.coderthoughts.asciipics.client;

import java.util.concurrent.TimeUnit;

import org.coderthoughts.asciipics.api.PictureService;

public class ClientComponent {
    private PictureService pictureService;
    private volatile boolean keepRunning;

    public void activate() {
        System.out.println("************************ activated");

        keepRunning = true;
        new Thread(new DilbertPrinter()).start();
    }

    public void deactivate() {
        System.out.println("####################### deactivated");

        keepRunning = false;
    }

    public PictureService getPictureService() {
        return pictureService;
    }

    public void setPictureService(PictureService ps) {
        pictureService = ps;
    }

    private class DilbertPrinter implements Runnable {
        @Override
        public void run() {
            while (keepRunning) {
                System.out.println();
                System.out.println(pictureService.getPic("Dilbert"));

                try {
                    TimeUnit.SECONDS.sleep(5);
                } catch (InterruptedException e) {
                    // oh well
                }
            }
        }
    }
}
