import example.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static example.Box.printBox;

public class Test {
    public static void main(String[] args) {
        /*
         *    БАЗА
         */
        Integer[] intArray = {1, 2, 3, 4, 5, 6, 7, 8, 9};
        Character[] charArray = {'a', 'b', 'c'};

        swap(intArray, 2, 6);
        swap(charArray, 0, 2);

        List<Integer> intList = convertToList(intArray);
        List<Character> charList = convertToList(charArray);


        /*
         *    ВАРИАНТ
         */

        System.out.println(intList);
        System.out.println(charList);


        Box<Apple> appleBox1 = new Box<>(new Apple(), new Apple(), new Apple(), new Apple());

        Box<Apple> appleBox2 = new Box<>(new Apple(), new Apple(), new Apple());

        Box<Orange> orangeBox1 = new Box<>(new Orange(), new Orange(), new Orange());

        Box<Orange> orangeBox2 = new Box<>(new Orange(), new Orange(), new Orange(), new Orange(), new Orange());

        appleBox1.addFruit(2);
        orangeBox1.addFruit(1);
        appleBox2.addFruit(3);


        printBox(appleBox1);
        printBox(appleBox2);
        System.out.println("Масса коробки с яблоками 1 равна массе коробка с яблоками 2 = "
                + appleBox1.compareBoxesWeight(appleBox2));
        printBox(appleBox2);
        printBox(orangeBox1);
        System.out.println("Масса коробки с яблоками 2 равна массе коробки с апельсинами 1 = "
                + appleBox2.compareBoxesWeight(orangeBox1));

        appleBox1.transferFruit(appleBox2);


        Box<Lemon> lemonBox1 = new Box<>(new Lemon());

        Box<Lemon> lemonBox2 = new Box<>(new Lemon(), new Lemon(), new Lemon(), new Lemon());

        lemonBox2.addFruit(2, new Apple());
        lemonBox2.addFruit(1, new Orange());
        lemonBox2.addFruit(1, new Lemon());

        lemonBox2.transferFruit(lemonBox1);

        lemonBox1.transferFruit(lemonBox1);


//        Box<Apple> apple1 = new Box<>();
        lemonBox1.transferFruit(lemonBox1);

    }

    public static <T> void swap(T[] array, int firstIndex, int secondIndex) {
        T temp = array[firstIndex];
        array[firstIndex] = array[secondIndex];
        array[secondIndex] = temp;
    }

    public static <T> List<T> convertToList(T[] array) {
        return new ArrayList<>(Arrays.asList(array));
    }
}

