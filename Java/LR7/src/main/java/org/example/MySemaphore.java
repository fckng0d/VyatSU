package org.example;

import java.util.concurrent.Semaphore;

public class MySemaphore extends Semaphore {
    public MySemaphore(int permits){
        super(permits);
    }

    public void reducePermits(int reduction){
        super.reducePermits(reduction);
    }
}

