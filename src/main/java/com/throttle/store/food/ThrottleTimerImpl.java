package com.throttle.store.food;

import java.util.Timer;
import java.util.TimerTask;

public class ThrottleTimerImpl implements Throttler {

    private final int throttlePeriod;
    private final OrderCount callsCount;

    public ThrottleTimerImpl(int throttlePeriod, OrderCount callsCount) {
        this.throttlePeriod = throttlePeriod;
        this.callsCount = callsCount;
    }

    @Override
    public void start() {
        new Timer(true).schedule(new TimerTask() {
            @Override
            public void run() {
                callsCount.reset();
            }
        }, 0, throttlePeriod);
    }
}
