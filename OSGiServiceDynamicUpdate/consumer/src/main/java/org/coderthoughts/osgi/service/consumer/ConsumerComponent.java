/*
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.coderthoughts.osgi.service.consumer;

import java.util.concurrent.TimeUnit;

import org.coderthoughts.osgi.services.api.MyService;

public class ConsumerComponent {
    volatile boolean keepRunning = true;
    private ConsumerWindow consumerWindow;
    private MyService service;

    public void activate() {
        consumerWindow = new ConsumerWindow();
        consumerWindow.setVisible(true);

        keepRunning = true;
        new Thread(new ServiceCaller()).start();
        System.out.println("*** Activated component");
    }

    public void setMyService(MyService svc) {
        System.out.println("*** Setting service: " + svc);
        service = svc;
    }

    public void deactivate() {
        System.out.println("*** Deactivating component");
        keepRunning = false;

        consumerWindow.setVisible(false);
        consumerWindow = null;
    }

    private class ServiceCaller implements Runnable {
        private int counter = 0;

        @Override
        public void run() {
            StringBuilder sb = new StringBuilder();
            while (keepRunning) {
                if (counter % 10 == 0)
                    sb.setLength(0);

                sb.append(++counter);
                sb.append(": ");
                sb.append(service.performOperation("request"));
                sb.append("\n");

                consumerWindow.setText(sb.toString());

                try {
                    TimeUnit.SECONDS.sleep(4);
                    consumerWindow.setText("");
                    TimeUnit.MILLISECONDS.sleep(300);
                } catch (InterruptedException e) {
                }
            }
        }
    }
}
