package org.example.animals;


import org.example.animals.Animal;

public abstract class Marsupials extends Animal {
    private String diet;

    private static int countAll = 0;

    public Marsupials(String name, int age, String diet) {
        super(name, age);
        this.diet = diet;
        countAll++;
    }

    public void benefit() {
        System.out.println("Рацион " + super.getName() + ":\n" + diet);
    }

    public static int getCountAll() {
        return countAll;
    }
}
