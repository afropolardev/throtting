package com.throttle.store.food;


import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

@Slf4j
public class Main {
    public static void main(String[] args) {
        var orderCount = new OrderCount();
        var son = new FoodClient("son", 2, orderCount);
        var father = new FoodClient("father", 4, orderCount);

        var executorService = Executors.newFixedThreadPool(2);

        executorService.execute(() -> makeServiceCalls(son, orderCount));
        executorService.execute(() -> makeServiceCalls(father, orderCount));

        executorService.shutdown();
        try {
            executorService.awaitTermination(10, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            log.error("Executor service terminated: {}", e.getMessage());
        }
    }

    private static void makeServiceCalls(FoodClient foodClient, OrderCount orderCount) {
        var timer = new ThrottleTimerImpl(1000, orderCount);
        var service = new Waiter(timer, orderCount);
        // Sleep is introduced to keep the output in check and easy to view and analyze the results.
        IntStream.range(0, 50).forEach(i -> {
            service.orderDrink(foodClient);
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                log.error("Thread interrupted: {}", e.getMessage());
            }
        });
    }
}