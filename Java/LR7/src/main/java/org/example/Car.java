package org.example;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.atomic.AtomicInteger;

public class Car implements Runnable {
    private static CyclicBarrier barrier;
    private static CyclicBarrier cbf = new CyclicBarrier(1);
    private static CountDownLatch cf;
    private static int CARS_COUNT;
    private int position;
    private static AtomicInteger atom = new AtomicInteger(0);

    static {
        CARS_COUNT = 0;
    }

    private Race race;
    private int speed;
    private String name;
    public String getName() {
        return name;
    }

    public int getSpeed() {
        return speed;
    }

    public Car(Race race, int speed, CyclicBarrier cb, CountDownLatch cf) {
        this.race = race;
        this.speed = speed;
        this.barrier = cb;
        this.cf= cf;
        CARS_COUNT++;
        this.name = "Участник #" + CARS_COUNT;
    }

    @Override
    public void run() {
        try {
            System.out.println(this.name + " готовится");
            Thread.sleep(500 + (int) (Math.random() * 8));
            System.out.println(this.name + " готов");
            barrier.await();
            for (int i = 0; i < race.getStages().size(); i++) {
                race.getStages().get(i).go(this);
                if (i == race.getStages().size() - 1) {
                    getWin();
                }
            }
            cf.countDown();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public synchronized void getWin() {
        position = atom.incrementAndGet();
        if (position == 1) {
            System.out.println("ВАЖНОЕ ОБЪЯВЛЕНИЕ >>> Победил " + name + "!");
        } else if (position <= 3) {
            System.out.println("ВАЖНОЕ ОБЪЯВЛЕНИЕ >>> " + name + " занял " + position + " место!");
        }
        try {
            barrier.await();
        } catch (InterruptedException | BrokenBarrierException e) {
            e.printStackTrace();
        }
    }
}
