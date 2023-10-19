package vyatsu.task1;

public class Dog extends Animal {
    private static final int RUN_LIMIT = 500;
    private static final int SWIM_LIMIT = 10;
    private static int countOfDog;

    public Dog(String name, int age) {
        super(name, age);
        super.setMaxRunDist(RUN_LIMIT);
        super.setMaxSwimDist(SWIM_LIMIT);
        countOfDog++;
        Animal.increment();
    }

    public static int getCountOfDog() {
        return countOfDog;
    }

}
