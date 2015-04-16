package org.coderthoughts.srperf.impl;

import java.util.List;

import org.coderthoughts.srperf.api.Runner;

class Stopper extends Thread {
    private final long duration;
    private final List<Runner> runners;
    private final String type;
    private volatile double result = -1;

    Stopper(List<Runner> runners, long duration) {
        this.duration = duration;
        this.runners = runners;
        this.type = runners.iterator().next().getClass().getSimpleName();
    }

    @Override
    public void run() {
        try {
            Thread.sleep(duration);
        } catch (InterruptedException e1) {
            e1.printStackTrace();
        }

        for (Runner r : runners)
            r.stopRunner();

        for (Runner r : runners) {
            try {
                r.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        double total = 0;
        for (Runner r : runners) {
            System.out.printf("Result (%s): %f\n", r.getClass().getSimpleName(), r.getResult());
            total += r.getResult();
        }
        result = total;
        System.out.printf("Total result (%d ms): %f\n", duration, total);
    }

    double getResult() {
        return result;
    }

    String getType() {
        return type;
    }
}