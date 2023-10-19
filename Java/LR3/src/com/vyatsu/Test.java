package com.vyatsu;

import com.vyatsu.example.*;


public class Test {
    public static void main(String[] args) throws TestException {
        String[][] array = {
                {"6", "8.0", "3", "2"},
                {"6", "6.", "6", "3"},
                {"4", "6.0", "6", "3"},
                {"6", "8", "3", "3"}
        };
        sumArray(array);
    }


    public static boolean checkSizeArray(String[][] array) throws MyArraySizeException {
        try {
            for (int i = 0; i < array.length; i++) {
                if (array.length != 4 || array[i].length != 4) {
                    throw new MyArraySizeException();
                }
            }
        } catch (MyArraySizeException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public static void sumArray(String[][] array) throws MyArrayDataException {
        if (!checkSizeArray(array)) {
            return;
        }

        float sum = 0;
        boolean exceptionThrown = false;

        for (int i = 0; i < array.length; i++) {
            for (int j = 0; j < array.length; j++) {
                try {
                    try {
                        if (array[i][j].endsWith(".") || array[i][j].matches("-?\\d+(\\.\\d+)?")) {
                            sum += Float.parseFloat(array[i][j]);
                        }
                    } catch (NumberFormatException e) {
                        exceptionThrown = true;
                        throw new MyArrayDataException(i, j);
                    }
                } catch (MyArrayDataException e) {
                    e.printStackTrace();
                }
            }
        }

        if (exceptionThrown) {
            return;
        } else {
            isArrayHamming(array);
        }
        System.out.println("Сумма элементов матрицы = " + sum);


    }

    public static void isArrayHamming(String[][] array) throws MyNoHammingException {
        float num = 0;
        boolean exceptionThrown2 = false;
        for (int i = 0; i < array.length; i++) {
            for (int j = 0; j < array.length; j++) {
                num = Float.parseFloat(array[i][j]);
                try {
                    if (num > 0 && num <= 1000 && !isHamming(num)) {
                        exceptionThrown2 = true;
                        throw new MyNoHammingException(i, j);
                    }
                } catch (MyNoHammingException e) {
                    e.printStackTrace();
                }

            }
        }

        if (exceptionThrown2) {
            return;
        }
        System.out.println("Элементы матрицы принадлежат последовательности Хемминга в пределах 1000");
    }

    public static boolean isHamming(float num) throws MyNoHammingException {
        if (num <= 0) {
            return false;
        }
        while (num % 2 == 0) {
            num /= 2;
        }
        while (num % 3 == 0) {
            num /= 3;
        }
        while (num % 5 == 0) {
            num /= 5;
        }
        if (num == 1) {
            return true;
        } else {
            return false;
        }
    }
}

