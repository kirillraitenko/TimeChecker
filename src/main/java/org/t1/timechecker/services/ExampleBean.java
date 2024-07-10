package org.t1.timechecker.services;

import org.springframework.stereotype.Service;
import org.t1.timechecker.annotation.TrackAsyncTime;
import org.t1.timechecker.annotation.TrackTime;

import java.util.Random;

@Service
public class ExampleBean {
    private final Random random = new Random();

    @TrackTime
    public void testMethodSync(){
        wait(50, 100);
    }
    @TrackAsyncTime
    public void testMethodAsync(){
        wait(50, 100);
    }

    private void wait(int start, int bound) {
        try {
            int mills = random.nextInt(start, bound);
            Thread.sleep(mills);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
