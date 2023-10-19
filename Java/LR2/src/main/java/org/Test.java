package main.java.org;
import main.java.org.example.*;

public class Test {
    public static void main(String[] args) {
        Contestant[] contestants = {
                new Human("Genry", 2, 15),
                new Human("John", 3, 25),
                new Cat("Barsik", 2, 15),
                new Cat("Murzik", 4, 15),
                new Cat("Cake", 4, 15),
                new Robot("R2-D2", 7, 20),
                new Robot("Rob", 15, 80)
        };

        Obstacle[] obstacles = {
                new Wall(Height.HIGH),
                new Treadmill(Distance.SHORT),
                new Wall(Height.HIGH),
                new Treadmill(Distance.MEDIUM),
                new Wall(Height.HIGH),
                new Treadmill(Distance.LONG)
        };

        for (Contestant contestant : contestants) {
            for (Obstacle obstacle : obstacles) {
                if (!obstacle.PassObstacle(contestant)) {
                    break;
                }
            }
            System.out.println("==========================================");
        }
    }
}
