package org.coderthoughts.srperf.impl;


class ResultCollector extends Thread {
    final Stopper[] stoppers;

    ResultCollector(Stopper ... threads) {
        this.stoppers = threads;
    }

    @Override
    public void run() {
        for (Stopper s : stoppers) {
            try {
                s.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        System.out.println("\n========= Results =========");

        double aggregate = 0;
        for (Stopper s : stoppers) {
            System.out.printf("%s: %f\n", s.getType(), s.getResult());
            aggregate += s.getResult();
        }

        System.out.printf("\nAggregate result: %f", aggregate);
    }
}