package main.java.org.example;

public enum Height {
    LOW(2), MEDIUM(5), HIGH(10);

    private int height;

    Height(int height) {
        this.height = height;
    }

    public int getHeight() {
        return height;
    }
}
