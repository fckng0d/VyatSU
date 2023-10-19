import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MyThread {
    static final int SIZE = 60_000_004;
    static final int HALF = SIZE / 2;

    public void firstMethod() {
        float[] array = new float[SIZE];
        Arrays.fill(array, 1.0f);

        long start = System.currentTimeMillis();

        for (int i = 0; i < SIZE; i++) {
            array[i] = (float) (array[i] * Math.sin(0.2f + i / 5) * Math.cos(0.2f + i / 5) *
                    Math.cos(0.4f + i / 2));
        }

        long end = System.currentTimeMillis();
        System.out.println("Первый элемент массива:    " + array[0]);
        System.out.println("Последний элемент массива: " + array[SIZE - 1]);
        System.out.println("Время выполнения перового метода: " + (end - start) + "\n");
    }

    public void secondMethod() {
        float[] array = new float[SIZE];
        float[] firstHalf = new float[HALF];
        float[] secondHalf = new float[HALF];
        Arrays.fill(array, 1.0f);

        long start = System.currentTimeMillis();

        System.arraycopy(array, 0, firstHalf, 0, HALF);
        System.arraycopy(array, HALF, secondHalf, 0, HALF);

        Thread thread1 = new Thread(() -> {
            for (int i = 0; i < HALF; i++) {
                firstHalf[i] = (float) (firstHalf[i] * Math.sin(0.2f + i / 5) *
                        Math.cos(0.2f + i / 5) * Math.cos(0.4f + i / 2));
            }
            System.arraycopy(firstHalf, 0, array, 0, HALF);
        });

        Thread thread2 = new Thread(() -> {
            for (int i = HALF; i < SIZE; i++) {
                secondHalf[i - HALF] = (float) (secondHalf[i - HALF] * Math.sin(0.2f + i / 5) *
                        Math.cos(0.2f + i / 5) * Math.cos(0.4f + i / 2));
            }
            System.arraycopy(secondHalf, 0, array, HALF, HALF);
        });

        thread1.start();
        thread2.start();

        try {
            thread1.join();
            thread2.join();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        long end = System.currentTimeMillis();

        System.out.println("Первый элемент массива:    " + array[0]);
        System.out.println("Последний элемент массива: " + array[SIZE - 1]);
        System.out.println("Время выполнения второго метода: " + (end - start) + "\n");
    }

    public void thirdMethod(int threadCount) {
        float[] array = new float[SIZE];
        Arrays.fill(array, 1.0f);

        while (SIZE % threadCount != 0) {
            threadCount++;
        }

        float[][] subArrays = new float[threadCount][];
        List<Thread> threads = new ArrayList<>();

        long startTime = System.currentTimeMillis();

        int startIndex = 0;
        int endIndex = 0;
        int threadSize = 0;

        for (int i = 0; i < threadCount; i++) {
            threadSize = SIZE / threadCount;

            startIndex = endIndex;
            endIndex += threadSize;

            final int start = startIndex;
            final int end = endIndex;
            final int arrayIndex = i;

            threads.add(new Thread(() -> {
                subArrays[arrayIndex] = Arrays.copyOfRange(array, start, end);
                for (int j = start; j < end; j++) {
                    subArrays[arrayIndex][j - start] = (float) (subArrays[arrayIndex][j - start] * Math.sin(0.2f + j / 5) *
                            Math.cos(0.2f + j / 5) * Math.cos(0.4f + j / 2));
                }
                System.out.println(subArrays[arrayIndex].length);
            }));
            threads.get(i).start();
        }

        for (Thread thread : threads) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

        for (int i = 0; i < threadCount; i++) {
            System.arraycopy(subArrays[i], 0, array, i * threadSize, threadSize);
        }

        long endTime = System.currentTimeMillis();

        System.out.println("Первый элемент массива:    " + array[0]);
        System.out.println("Последний элемент массива: " + array[SIZE - 1]);
        System.out.println("Время выполнения третьего метода: " + (endTime - startTime) + "\n");
    }
}
