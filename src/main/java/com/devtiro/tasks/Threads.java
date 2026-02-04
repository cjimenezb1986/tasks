package com.devtiro.tasks;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;

public class Threads {

    public static void main(String[] args)  throws InterruptedException {
        int n = 300_000;

        System.out.println("Virtual:");
        runVirtual(n);

        System.out.println("\nPlatform:");
        runPlatform(n);
    }
    static void runVirtual(int n) throws InterruptedException {
        var latch = new CountDownLatch(n);
        var counter = new AtomicInteger();

        long start = System.nanoTime();
        for (int i = 0; i < n; i++) {
            Thread.startVirtualThread(() -> {
                counter.incrementAndGet();
                latch.countDown();
            });
        }
        latch.await();
        long end = System.nanoTime();

        double totalMs = (end - start) / 1_000_000;
        System.out.println("Terminados: " + counter.get());
        System.out.println("Tiempo total (ms): " + totalMs);
    }
    static void runPlatform(int n) throws InterruptedException {
        var latch = new CountDownLatch(n);
        var counter = new AtomicInteger();

        long start = System.nanoTime();
        for (int i = 0; i < n; i++) {
            new Thread(() -> {
                counter.incrementAndGet();
                latch.countDown();
            }).start();
        }
        latch.await();
        long end = System.nanoTime();

        double totalMs = (end - start) / 1_000_000;
        System.out.println("Terminados: " + counter.get());
        System.out.println("Tiempo total (ms): " + totalMs);
    }
}
