package org.example;

import org.example.animals.*;

import java.util.Random;

public class Main {
    public static void main(String[] args) {
        Animal[] animals = {
                new Tyrannosaurus("Рэкс", 13, 10.4f),
                new Diplodocus("Дино", 6, 15.6f),
                new Stegosaurus("Застёжка", 8, 4.5f),
                new Possum("Мусоп", 2, "Падаль, грибы, кукуруза, зерновые"),
                new Kangaroo("Кенга", 8, "Трава и фрукты"),
                new Wombat("Вобла", 3, "Трава, грибы, ягоды")
        };

        Random random = new Random();
        for (Animal animal : animals) {
            animal.run(random.nextInt(1, 600));
            animal.swim(random.nextInt(1, 20));
            System.out.println();
        }
        System.out.println();

        System.out.println("Количество всех животных = " + Animal.getCountOfAnimal());
        System.out.println("Количество всех сумчатых = " + Marsupials.getCountAll());
        System.out.println("Количество всех динозавров = " + Dinosaur.getCountAll());

        System.out.println("Количество всех стегозавров = " + Stegosaurus.getCountOfStegosaurs());
        System.out.println("Количество всех тирэксов = " + Tyrannosaurus.getCountOfTyrannosaurs());
        System.out.println("Количество всех диплодоков = " + Diplodocus.getCountOfDiplodocuses());



//        for (int i = 0; i <= 2; i++) {
//            ((Dinosaur) animals[i]).weight();
//        }
//        System.out.println();
//        for (int i = 3; i < animals.length; i++) {
//            ((Marsupials) animals[i]).benefit();
//        }

    }
}