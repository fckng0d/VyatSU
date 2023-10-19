package vyatsu.task1;

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

    public void run(int distance) {
        if (distance > maxRunDist) {
            System.out.println(name + " не может пробежать " + distance + " м");
        } else {
            System.out.println(name + " пробежал " + distance + " м");
        }
    }

    public void swim(int distance) {
        if (maxSwimDist == 0) {
            System.out.println(name + " не умеет плавать");
        } else {
            if (distance > maxSwimDist) {
                System.out.println(name + " не может проплыть " + distance + " м");
            } else {
                System.out.println(name + " проплыл " + distance + " м");
            }
        }
    }

    public static int getCountOfAnimal() {
        return countOfAnimal;
    }

    public static void increment() {
       Animal.countOfAnimal++;
    }

    public String getName() {
        return name;
    }

    public void setMaxRunDist(int maxRunDist) {
        this.maxRunDist = maxRunDist;
    }

    public void setMaxSwimDist(int maxSwimDist) {
        this.maxSwimDist = maxSwimDist;
    }
}
