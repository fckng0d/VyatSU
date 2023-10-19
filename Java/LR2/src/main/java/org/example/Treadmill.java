package main.java.org.example;

public class Treadmill implements Obstacle {
    Distance distance;

    public Treadmill(Distance distance) {
        this.distance = distance;
    }

    @Override
    public boolean PassObstacle(Contestant contestant) {
        if (contestant.run(distance.getDistance())) {
            System.out.printf("%s пробежал дорожку длиной %dм \n\n", contestant.getName(), distance.getDistance());
            return true;
        } else {
            System.out.printf("%s НЕ пробежал дорожку длиной %dм \n\n", contestant.getName(), distance.getDistance());
            return false;
        }
    }
}
