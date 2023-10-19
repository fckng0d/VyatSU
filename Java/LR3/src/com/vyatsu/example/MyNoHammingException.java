package com.vyatsu.example;

public class MyNoHammingException extends TestException {
    private int rows;
    private int cols;

    public MyNoHammingException(int rows, int cols) {
        super("Элемент матрицы в строке " + (rows + 1) + " в столбце " +
                (cols + 1) + " не принадлежит последовательности Хемминга");
    }
}
