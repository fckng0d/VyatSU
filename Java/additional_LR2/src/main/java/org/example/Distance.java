package main.java.org.example;

public enum Distance {
    SHORT(5), MEDIUM(20), LONG(50);

    private int distance;

    Distance(int distance) {
        this.distance = distance;
    }

    public int getDistance() {
        return distance;
    }
}
