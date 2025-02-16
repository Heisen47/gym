package com.example.gym.util;

import java.util.concurrent.atomic.AtomicLong;

public class CustomIdGenerator {
    private static final AtomicLong sequence = new AtomicLong(1000000000L);

    public static Long generateId() {
        return sequence.getAndIncrement();
    }
}