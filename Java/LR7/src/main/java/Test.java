import org.example.Car;
import org.example.Race;
import org.example.Road;
import org.example.Tunnel;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.CyclicBarrier;

public class Test {
    public static final int CARS_COUNT = 5;
    private static final CyclicBarrier BARRIER = new CyclicBarrier(CARS_COUNT + 1);
    private static final CountDownLatch FINAL = new CountDownLatch(CARS_COUNT);

    public static void main(String[] args) {
        System.out.println("ВАЖНОЕ ОБЪЯВЛЕНИЕ >>> Подготовка!!!");
        Race race = new Race(new Road(60), new Tunnel(), new Road(40));
        Car[] cars = new Car[CARS_COUNT];
        for (int i = 0; i < cars.length; i++) {
            cars[i] = new Car(race, 20 + (int) (Math.random() * 10), BARRIER, FINAL);
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
            FINAL.await();
            System.out.println("ВАЖНОЕ ОБЪЯВЛЕНИЕ >>> Гонка закончилась!!!");
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
