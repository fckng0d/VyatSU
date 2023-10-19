package vyatsu.task2;

import vyatsu.task1.*;

public abstract class Marsupials extends Animal {
    private String diet;

    public Marsupials(String name, int age, String diet) {
        super(name, age);
        this.diet = diet;
    }

    public void benefit() {
        System.out.println("Рацион " + super.getName() + ":\n" + diet);
    }


}
