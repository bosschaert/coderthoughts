package org.coderthoughts.async;

public class TimeConsumingServiceImpl implements TimeConsumingService {
    @Override
    public int bigTask(int i) {
        try {
            Thread.sleep(1000);
        } catch (Exception e) {
        }
        return i+1;
    }

}
