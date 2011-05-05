package org.coderthoughts.asciipics.client;

import java.util.Date;
import java.util.concurrent.TimeUnit;

import org.coderthoughts.asciipics.api.PictureService;

public class ClientComponent {
    private ClientWindow clientWindow;
    private PictureService pictureService;
    private volatile boolean keepRunning;

    public void activate() {
        clientWindow = new ClientWindow();
        clientWindow.setVisible(true);

        keepRunning = true;
        new Thread(new DilbertPrinter()).start();
    }

    public void deactivate() {
        clientWindow.setVisible(false);
        clientWindow = null;

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
                StringBuilder sb = new StringBuilder(new Date().toString());
                sb.append('\n');
                sb.append(pictureService.getPic("Dilbert"));
                clientWindow.setText(sb.toString());

                try {
                    TimeUnit.SECONDS.sleep(4);
                    clientWindow.setText("");
                    TimeUnit.MILLISECONDS.sleep(300);
                } catch (InterruptedException e) {
                    // oh well
                }
            }
        }
    }
}
