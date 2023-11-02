package org.example;

import java.util.concurrent.Semaphore;

public class Tunnel extends Stage {
    private static final Semaphore smp = new Semaphore(2);

    public Tunnel() {
        this.length = 80;
        this.description = "Тоннель " + length + " метров";
    }

    @Override
    public void go(Car c, int numStage, Race race) throws InterruptedException {
        try {
//            resultSmp.acquire();
            System.out.println(c.getName() + " готовится к этапу(ждет): " + description);
//            resultSmp.release();

            smp.acquire();

            resultSmp.acquire();
            System.out.println(c.getName() + " начал этап: " + description);
            resultSmp.release();
            Thread.sleep(length / c.getSpeed() * 1000L);

            resultSmp.acquire();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            System.out.println(c.getName() + " закончил этап: " + description);
            smp.release();
        }
    }
}
