package vyatsu.task1;

public class Cat extends Animal {
    private static final int RUN_LIMIT = 200;
    private static final int SWIM_LIMIT = 0;
    private static int countOfCat;

    public Cat(String name, int age) {
        super(name, age);
        super.setMaxRunDist(RUN_LIMIT);
        super.setMaxSwimDist(SWIM_LIMIT);
        countOfCat++;
        Animal.increment();
    }

    public static int getCountOfCat() {
        return countOfCat;
    }
}