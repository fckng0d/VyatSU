package org.example;

import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.locks.ReentrantLock;

public abstract class Stage {
    protected int length;
    protected String description;
    protected static final ReentrantLock locker = new ReentrantLock();
    public static final CyclicBarrier resultBarrier = new CyclicBarrier(1);
    public static final CyclicBarrier resultBarrier2 = new CyclicBarrier(100);
    public static CyclicBarrier barrier = resultBarrier;
    public static MySemaphore smp2 = new MySemaphore(20);

    public abstract void go(Car c, int numStage, Race race);

    public static void waitUnlock() throws InterruptedException {
        while (true) {
//            if (!(locker.isLocked()) ) break;
            if (/*!(locker.isLocked()) && */barrier.getParties() == 1) break;
        }
    }
}
