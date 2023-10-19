package vyatsu.task2;

import vyatsu.task1.Animal;

public class Kangaroo extends Marsupials {
    private static final int RUN_LIMIT = 150;
    private static final int SWIM_LIMIT = 0;
    private static int countOfKangaroo;

    public Kangaroo(String name, int age, String diet) {
        super(name, age, diet);
        super.setMaxRunDist(RUN_LIMIT);
        super.setMaxSwimDist(SWIM_LIMIT);
        countOfKangaroo++;
        Animal.increment();
    }

    public static int getCountOfKangaroo() {
        return countOfKangaroo;
    }
}
