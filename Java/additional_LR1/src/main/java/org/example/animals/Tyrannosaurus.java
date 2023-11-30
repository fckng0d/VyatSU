package org.example.animals;

public class Tyrannosaurus extends Dinosaur {
    private static final int RUN_LIMIT = 750;
    private static final int SWIM_LIMIT = 0;
    private static int countOfTyrannosaurs;


    public Tyrannosaurus(String name, int age, float averageWeight) {
        super(name, age, averageWeight);
        super.setMaxRunDist(RUN_LIMIT);
        super.setMaxSwimDist(SWIM_LIMIT);
        countOfTyrannosaurs++;
        Animal.increment();
    }

    public static int getCountOfTyrannosaurs() {
        return countOfTyrannosaurs;
    }
}
