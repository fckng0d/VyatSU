package org.example.animals;

public class Stegosaurus extends Dinosaur {
    private static final int RUN_LIMIT = 350;
    private static final int SWIM_LIMIT = 0;
    private static int countOfStegosaurs;

    public Stegosaurus(String name, int age, float averageWeight) {
        super(name, age, averageWeight);
        super.setMaxRunDist(RUN_LIMIT);
        super.setMaxSwimDist(SWIM_LIMIT);
        countOfStegosaurs++;
        Animal.increment();
    }

    public static int getCountOfStegosaurs() {
        return countOfStegosaurs;
    }
}
