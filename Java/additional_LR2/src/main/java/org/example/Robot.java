package main.java.org.example;

public class Robot implements Contestant {
    private String name;
    private int maxJumpHeight;
    private int maxRunDistance;

    public Robot(String name, int maxJumpHeight, int maxRunDistance) {
        this.name = name;
        this.maxJumpHeight = maxJumpHeight;
        this.maxRunDistance = maxRunDistance;
    }

    @Override
    public boolean jump(Height height, int maxJumpHeight) {
        if (maxJumpHeight >= height.getHeight()) {
            System.out.printf("%s перепрыгнул стену высотой %dм \n\n", this.getName(), height.getHeight());
            return true;
        }
        System.out.printf("%s НЕ перепрыгнул стену высотой %dм \n\n", this.getName(), height.getHeight());
        return false;
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
