package main.java.org.example;

public class Wall implements Obstacle {
    Height height;

    public Wall(Height height) {
        this.height = height;
    }

    @Override
    public boolean PassObstacle(Contestant contestant) {
        return contestant.jump(height, contestant.getMaxJumpHeight());
    }
}
