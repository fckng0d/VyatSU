package main.java.org.example;

public interface AbleToSuperJump {
    default boolean superJump(String name, Height height) {
        int superJumpCharges = 0;
        if (this instanceof Cat) {
            superJumpCharges = --Cat.superJumpCharges;
        } else if (this instanceof Human) {
            superJumpCharges = --Human.superJumpCharges;
        }
        System.out.printf("%s использовал суперпрыжок (осталось " + superJumpCharges + ") и" +
                " перепрыгнул стену высотой %dм \n\n", name, height.getHeight());
        return true;
    };
}

