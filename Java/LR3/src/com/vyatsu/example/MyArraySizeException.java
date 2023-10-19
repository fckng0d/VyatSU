package com.vyatsu.example;

public class MyArraySizeException extends TestException {
    public MyArraySizeException() {
        super("Размер матрицы должен быть 4х4");
    }
}
