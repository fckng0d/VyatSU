package main.java.org.example;

public class Cat implements Contestant, ableToSuperJump {
    private String name;
    private int maxJumpHeight;
    private int maxRunDistance;
    public static int superJumpCharges = 2;

    public Cat(String name, int maxJumpHeight, int maxRunDistance) {
        this.name = name;
        this.maxJumpHeight = maxJumpHeight;
        this.maxRunDistance = maxRunDistance;
    }

    @Override
    public boolean jump(Height height, int maxJumpHeight) {
        if (maxJumpHeight < height.getHeight()) {
            if (superJumpCharges > 0) {
                return superJump(name, height);
            } else {
                System.out.printf("%s НЕ перепрыгнул стену высотой %dм \n\n", name, height.getHeight());
                return false;
            }
        } else {
            System.out.printf("%s перепрыгнул стену высотой %dм \n\n", name, height.getHeight());
            return true;
        }
    }

    @Override
    public boolean run(int distance) {
        return maxRunDistance >= distance;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public int getMaxJumpHeight() {
        return maxJumpHeight;
    }
}
