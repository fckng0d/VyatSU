package org.example;

import java.util.concurrent.Semaphore;

public abstract class Stage {
    protected int length;
    protected String description;
    public static Semaphore resultSmp = new Semaphore(1);

    public abstract void go(Car c, int numStage, Race race) throws InterruptedException;
}
