package org.example;

public class Road extends Stage {
    public Road(int length) {
        this.length = length;
        this.description = "Дорога " + length + " метров";
    }

    @Override
    public void go(Car c, int numStage, Race race) {
        try {
            resultSmp.acquire();
            System.out.println(c.getName() + " начал этап: " + description);
            resultSmp.release();
            Thread.sleep(length / c.getSpeed() * 1000L);
            resultSmp.acquire();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            System.out.println(c.getName() + " закончил этап: " + description);
        }
    }
}

