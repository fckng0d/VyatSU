import org.example.*;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.Semaphore;

public class Test {
    public static final int CARS_COUNT = 7;
    private static final CyclicBarrier BARRIER = new CyclicBarrier(CARS_COUNT + 1);
    static Semaphore blocker = new Semaphore(1);

    public static void main(String[] args) {
        System.out.println("ВАЖНОЕ ОБЪЯВЛЕНИЕ >>> Подготовка!!!");
        Race race = new Race(new Road(60), new Tunnel(), new Road(40));
        Car[] cars = new Car[CARS_COUNT];
        for (int i = 0; i < cars.length; i++) {
            cars[i] = new Car(race, 20 + (int) (Math.random() * 200), BARRIER, BARRIER);
        }

        for (Car car : cars) {
            new Thread(car).start();
        }

        try {
            BARRIER.await();
            System.out.println("ВАЖНОЕ ОБЪЯВЛЕНИЕ >>> Гонка началась!!!");
        } catch (BrokenBarrierException | InterruptedException e) {
            throw new RuntimeException(e);
        }

        try {
            BARRIER.await();
            BARRIER.await();
            System.out.println("ВАЖНОЕ ОБЪЯВЛЕНИЕ >>> Гонка закончилась!!!");
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } catch (BrokenBarrierException e) {
            throw new RuntimeException(e);
        }


    }
}
