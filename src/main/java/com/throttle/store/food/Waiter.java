package com.throttle.store.food;


import lombok.extern.slf4j.Slf4j;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ThreadLocalRandom;
import java.util.logging.Logger;

@Slf4j
class Waiter {

    private final OrderCount orderCount;

    public Waiter(Throttler timer, OrderCount orderCount) {
        this.orderCount = orderCount;
        timer.start();
    }

    public int orderDrink(FoodClient foodClient) {
        var name = foodClient.getClientName();
        var count = orderCount.getCount(name);
        if (count >= foodClient.getOrdersPerMinute()) {
            log.error("I'm sorry {}, that would be all for this window!", name);
            return -1;
        }
        orderCount.increaseCount(name);
        log.debug("Serving beer to {} : [{} consumed] ", foodClient.getClientName(), count + 1);
        return getRandomCustomerId();
    }

    private int getRandomCustomerId() {
        return ThreadLocalRandom.current().nextInt(1, 10000);
    }
}
