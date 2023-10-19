package vyatsu.task2;

import vyatsu.task1.Animal;

public class Possum extends Marsupials {
    private static final int RUN_LIMIT = 60;
    private static final int SWIM_LIMIT = 4;
    private static int countOfPossum;

    public Possum(String name, int age, String diet) {
        super(name, age, diet);
        super.setMaxRunDist(RUN_LIMIT);
        super.setMaxSwimDist(SWIM_LIMIT);
        countOfPossum++;
        Animal.increment();
    }

    public static int getCountOfPossum() {
        return countOfPossum;
    }
}
