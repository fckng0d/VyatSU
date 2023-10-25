package org.example;

import java.util.concurrent.BrokenBarrierException;

public class Road extends Stage {
    public Road(int length) {
        this.length = length;
        this.description = "Дорога " + length + " метров";
    }

    @Override
    public void go(Car c, int numStage, Race race) {
        try {
//            System.out.println("дороге нужно " + barrier.getParties());
            waitUnlock();
            barrier.await();
//            waitUnlock();
//            System.out.println(barrier.getNumberWaiting());
//            smp2.acquire();
            System.out.println(c.getName() + " начал этап: " + description);
            Thread.sleep(length / c.getSpeed() * 1000L);
//            smp2.release(2);
//            System.out.println(barrier.getNumberWaiting());

            if (numStage == race.getStages().size() - 1) {
                waitUnlock();
//                locker.lock();
//                smp2.reducePermits(20);
                barrier = resultBarrier2;
                System.out.println(c.getName() + " закончил этап: " + description);
                c.getWin();
//                System.out.println(barrier.getNumberWaiting());
//                barrier = resultBarrier;
            } else {
                waitUnlock();
                barrier.await();
//                waitUnlock();
//                smp2.acquire();
                System.out.println(c.getName() + " закончил этап: " + description);
//                waitUnlock();
//                smp2.release(2);
            }
        } catch (InterruptedException | BrokenBarrierException e) {
            e.printStackTrace();
        }
    }
}
