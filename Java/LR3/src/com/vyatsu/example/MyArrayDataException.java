package com.vyatsu.example;

public class MyArrayDataException extends TestException {
    private int rows;
    private int cols;

    public MyArrayDataException(int rows, int cols) {
        super("Недопустимый элемент в строке " + (rows + 1) + " в столбце " + (cols + 1));
    }
}
