package org.example.animals;

public abstract class Animal {
    private String name;
    private int age;
    private int maxRunDist;
    private int maxSwimDist;
    private static int countOfAnimal;

    public Animal(String name, int age) {
        this.name = name;
        this.age = age;
    }

    protected static void increment() {
        countOfAnimal++;
    }

    public void run(int distance) {
        if (distance > maxRunDist) {
            System.out.println(name + " не может пробежать " + distance + "м");
        } else {
            System.out.println(name + " пробежал " + distance + "м");
        }
    }

    public void swim(int distance) {
        if (maxSwimDist == 0) {
            System.out.println(name + " не умеет плавать");
        } else {
            if (distance > maxSwimDist) {
                System.out.println(name + " не может проплыть " + distance + "м");
            } else {
                System.out.println(name + " проплыл " + distance + "м");
            }
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int getMaxRunDist() {
        return maxRunDist;
    }

    public void setMaxRunDist(int maxRunDist) {
        this.maxRunDist = maxRunDist;
    }

    public int getMaxSwimDist() {
        return maxSwimDist;
    }

    public void setMaxSwimDist(int maxSwimDist) {
        this.maxSwimDist = maxSwimDist;
    }

    public static int getCountOfAnimal() {
        return countOfAnimal;
    }

    public static void setCountOfAnimal(int countOfAnimal) {
        Animal.countOfAnimal = countOfAnimal;
    }
}
