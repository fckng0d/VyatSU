package org.example;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.Semaphore;

public class Tunnel extends Stage {
    private static final Semaphore smp = new Semaphore(2);

    public Tunnel() {
        this.length = 80;
        this.description = "Тоннель " + length + " метров";
    }

    @Override
    public void go(Car c, int numStage, Race race) {
        try {
//            System.out.println("перед тоннелем ждут " + barrier.getNumberWaiting());
//            System.out.println("тоннелю нужно " + barrier.getParties());
//            waitUnlock();
            waitUnlock();
            barrier.await();
//            smp2.acquire();
            System.out.println(c.getName() + " готовится к этапу(ждет): " + description);
//            waitUnlock();
//            smp2.release(2);

            smp.acquire();
//            waitUnlock();
            waitUnlock();
            barrier.await();
//            smp2.acquire();
            System.out.println(c.getName() + " начал этап: " + description);
            Thread.sleep(length / c.getSpeed() * 1000L);
//            smp2.release(2);
            if (numStage == race.getStages().size() - 1) {
//                waitUnlock();
//                smp2.acquire(smp2.availablePermits());
//                locker.lock();
                System.out.println(c.getName() + " закончил этап: " + description);
                c.getWin();
            } else {
//                waitUnlock();
                waitUnlock();
                barrier.await();
//                smp2.acquire();
                System.out.println(c.getName() + " закончил этап: " + description);
//                waitUnlock();
//                smp2.release(2);
            }
            smp.release();
        } catch (InterruptedException | BrokenBarrierException e) {
            e.printStackTrace();
        }
    }
}
