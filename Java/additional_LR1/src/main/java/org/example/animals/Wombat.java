package org.example.animals;

public class Wombat extends Marsupials {
    private static final int RUN_LIMIT = 30;
    private static final int SWIM_LIMIT = 5;
    private static int countOfWombat;

    public Wombat(String name, int age, String diet) {
        super(name, age, diet);
        super.setMaxRunDist(RUN_LIMIT);
        super.setMaxSwimDist(SWIM_LIMIT);
        countOfWombat++;
        Animal.increment();
    }

    public static int getCountOfWombat() {
        return countOfWombat;
    }
}
