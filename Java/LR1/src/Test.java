import vyatsu.task1.Animal;
import vyatsu.task1.Cat;
import vyatsu.task1.Dog;
import vyatsu.task1.Tiger;
import vyatsu.task2.Kangaroo;
import vyatsu.task2.Marsupials;
import vyatsu.task2.Possum;
import vyatsu.task2.Wombat;

import java.util.Random;


public class Test {
    public static void main(String[] args) {
        Animal[] animals = {
                new Dog("Бобик", 5),
                new Dog("Шарик", 3),
                new Dog("Хатико", 7),
                new Cat("Муся", 6),
                new Cat("Мурка", 2),
                new Tiger("Тигра", 13),
                new Possum("Мусоп", 2, "Падаль, грибы, кукуруза, зерновые"),
                new Kangaroo("Кенга", 8, "Трава и фрукты"),
                new Wombat("Вобла", 3, "Трава, грибы, ягоды")
        };


        Random random = new Random();
        for (Animal animal : animals) {
            animal.run(random.nextInt(1, 500));
            animal.swim(random.nextInt(1, 20));
            System.out.println();
        }

        ((Kangaroo) animals[7]).benefit();
        ((Marsupials) animals[8]).benefit();

        System.out.println("Количество собак: " + Dog.getCountOfDog());
        System.out.println("Количество кошек: " + Cat.getCountOfCat());
        System.out.println("Количество тигров: " + Tiger.getCountOfTiger());
        System.out.println("Количество опоссумов: " + Possum.getCountOfPossum());
        System.out.println("Количество кенгуру: " + Kangaroo.getCountOfKangaroo());
        System.out.println("Количество вомбатов: " + Wombat.getCountOfWombat());
        System.out.println("Всего животных: " + Animal.getCountOfAnimal());
    }
}

