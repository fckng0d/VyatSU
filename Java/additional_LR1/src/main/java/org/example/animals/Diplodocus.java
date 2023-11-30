package org.example.animals;

public class Diplodocus extends Dinosaur {
    private static final int RUN_LIMIT = 150;
    private static final int SWIM_LIMIT = 50;
    private static int countOfDiplodocuses;

    public Diplodocus(String name, int age, float averageWeight) {
        super(name, age, averageWeight);
        super.setMaxRunDist(RUN_LIMIT);
        super.setMaxSwimDist(SWIM_LIMIT);
        countOfDiplodocuses++;
        Animal.increment();

    }

    public static int getCountOfDiplodocuses() {
        return countOfDiplodocuses;
    }
}
