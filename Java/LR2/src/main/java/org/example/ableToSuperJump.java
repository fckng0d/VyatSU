package main.java.org.example;

public interface ableToSuperJump {
    default boolean superJump(String name, Height height) {
        Cat.superJumpCharges--;
        System.out.printf("%s использовал суперпрыжок (осталось " + (Cat.superJumpCharges) + ") и" +
                " перепрыгнул стену высотой %dм \n\n", name, height.getHeight());
        return true;
    };
}

