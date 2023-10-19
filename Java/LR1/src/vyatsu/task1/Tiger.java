package vyatsu.task1;

public class Tiger extends Animal {
    private static final int RUN_LIMIT = 700;
    private static final int SWIM_LIMIT = 40;
    private static int countOfTiger;

    public Tiger(String name, int age) {
        super(name, age);
        super.setMaxRunDist(RUN_LIMIT);
        super.setMaxSwimDist(SWIM_LIMIT);
        countOfTiger++;
        Animal.increment();
    }

    public static int getCountOfTiger() {
        return countOfTiger;
    }

}
