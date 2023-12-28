package main.java.org.example;

public interface Contestant {
    boolean jump(Height height, int maxJumpHeight);
    boolean run(int distance);
    String getName();

    int getMaxJumpHeight();
}
