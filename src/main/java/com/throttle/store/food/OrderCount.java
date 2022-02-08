package com.throttle.store.food;

import lombok.extern.slf4j.Slf4j;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Slf4j
public final class OrderCount {
    private final Map<String, AtomicLong> tenantCallsCount =
            new ConcurrentHashMap<>();

    public void addTenant(String name) {
        tenantCallsCount.putIfAbsent(name, new AtomicLong(0));
    }

    public void increaseCount(String name) {
        tenantCallsCount.get(name).incrementAndGet();
    }

    public long getCount(String tenantName) {
        return tenantCallsCount.get(tenantName).get();
    }

    public void reset() {
        tenantCallsCount.replaceAll((k, v) -> new AtomicLong(0));
    }
}
