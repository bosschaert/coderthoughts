package org.coderthoughts.srperf.api;

public interface Runner {
    void join() throws InterruptedException;
    double getResult();
    void stopRunner();
}
