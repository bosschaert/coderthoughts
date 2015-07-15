package org.coderthoughts.async;

public class TimeConsumingServiceImpl implements TimeConsumingService {
    @Override
    public int bigTask(int i) {
        try {
            Thread.sleep(2500);
        } catch (Exception e) {
        }
        return i+1;
    }

}
