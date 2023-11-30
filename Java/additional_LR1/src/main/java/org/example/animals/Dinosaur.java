package org.example.animals;

public class Dinosaur extends Animal {
    private float averageWeight;

    private static int countAll = 0;

    public Dinosaur(String name, int age, float averageWeight) {
        super(name, age);
        this.averageWeight = averageWeight;
        countAll++;
        Animal.increment();
    }

    public void weight() {
        System.out.println("Средний вес " + this.getClass().getSimpleName()
                + " по имени " +  super.getName() + " = " + averageWeight + " тонны");
    }

    public static int getCountAll() {
        return countAll;
    }
}
