import example.Shedule;

import java.util.*;

public class Test {
    public static void main(String[] args) {
        /*
         *   БАЗА
         * */
        String[] words = {"яблоко", "банан", "вишня",
                "киви", "виноград", "лимон", "манго",
                "груша", "персик", "ананас", "груша", "киви",
                "клубника", "арбуз", "яблоко", "ежевика",
                "вишня", "лимон", "киви", "абрикос"};

        Map<String, Integer> wordCount = new HashMap<>();

        for (String word : words) {
            wordCount.put(word, wordCount.getOrDefault(word, 0) + 1);
        }

        System.out.println(wordCount.entrySet());


        /*
         *   ВАРИАНТ
         */
        Shedule shedule = new Shedule();
        shedule.add("Вторник", "Поход в театр ради джавы");
        shedule.add("Вторник", "Джава лаба 6");
        shedule.add("Вторник", "Поход в магазин");
        shedule.add("Вторник", "Поездка на автобусе");

        shedule.add("Четверг", "Кино");
        shedule.add("Четверг", "Кино");

        shedule.add("Понедельник", "Какое-то мероприятие");

        shedule.add("Среда", "Математика");
        shedule.add("Среда", "Кино");

        shedule.add("Пятница", "Яблоко");
        shedule.add("Пятница", "Арбуз");

//        shedule.getEvents("Вторник");
        shedule.getEvents("Четверг");
//        shedule.getEvents("Понедельник");
//        shedule.getEvents("Среда");

        shedule.getDate("Кино");
//        shedule.getDate("Поход за грибами");

        shedule.printShedule();
    }
}
