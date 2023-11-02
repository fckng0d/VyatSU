package org.example;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.atomic.AtomicInteger;

import static org.example.Stage.*;

public class Car implements Runnable {
    private static CyclicBarrier startBarrier;
    private static CyclicBarrier finalBarrier;
    private static int CARS_COUNT;
    private static final AtomicInteger atom = new AtomicInteger(0);
    private static final Car[] winners = new Car[3];

    static {
        CARS_COUNT = 0;
    }

    private final Race race;
    private final int speed;
    private final String name;

    public String getName() {
        return name;
    }

    public int getSpeed() {
        return speed;
    }

    public Car(Race race, int speed, CyclicBarrier startBarrier, CyclicBarrier finalBarrier) {
        this.race = race;
        this.speed = speed;
        Car.startBarrier = startBarrier;
        Car.finalBarrier = finalBarrier;
        CARS_COUNT++;
        this.name = "Участник #" + CARS_COUNT;
    }

    @Override
    public void run() {
        try {
            System.out.println(this.name + " готовится");
            Thread.sleep(500 + (int) (Math.random() * 800));
            System.out.println(this.name + " готов");

            startBarrier.await();
            for (int i = 0; i < race.getStages().size(); i++) {
                race.getStages().get(i).go(this, i, race);
                if (i == race.getStages().size() - 1) {
                    getWin();
                }
                resultSmp.release();
            }
            finalBarrier.await();
            finalBarrier.await();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void getWin() {
        int position = atom.incrementAndGet();
        if (position <= 3) {
            winners[position - 1] = this;
        System.out.println("ВАЖНОЕ ОБЪЯВЛЕНИЕ >>> " + name + " занял " + position + " место!");
        }
    }

    public static void printWinners() {
        System.out.println("\nПобедители: ");
        for (int i = 0; i < winners.length; i++) {
            System.out.println((i + 1) + " место — " + winners[i].getName());
        }
    }
}
