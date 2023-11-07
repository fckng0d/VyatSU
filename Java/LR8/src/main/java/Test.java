import org.example.Product;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Test {
    public static void main(String[] args) {
        System.out.println("=== Задание 1 ===");

        String[] words = {"яблоко", "вишня", "томат", "вишня", "вишня", "вишня", "яблоко",
                "asd", "asd", "asd",
                "aaa", "aaa", "aaa"};

        Arrays.stream(words)
                .collect(Collectors.groupingBy(w -> w, Collectors.counting()))
                .entrySet().stream()
                .collect(Collectors.groupingBy(
                        Map.Entry::getValue,
                        Collectors.mapping(Map.Entry::getKey, Collectors.toList())))
                .entrySet().stream()
                .max(Map.Entry.comparingByKey())
                .map(entry -> entry.getValue().stream()
                        .filter(word -> word.length() == entry.getValue().stream()
                                .mapToInt(String::length).min()
                                .orElse(Integer.MAX_VALUE))
                        .collect(Collectors.joining(", ")))
                .ifPresent(System.out::println);



        System.out.println("\n=== Задание 2 ===");

        Product[] products = {
                new Product("Молоко", 56, 1, "Россия"),
                new Product("Хлеб ржаной", 30, 2, "Россия"),
                new Product("Сыр Костромской", 130, 224, "Россия"),
                new Product("Хлеб бородинский", 27, 4, "Россия"),
                new Product("Яблоко", 16, 35, "Россия"),
                new Product("Шоколад", 86.00, 43, "Дания"),
                new Product("Сыр", 170.27, 34, "Нидерланды"),
                new Product("Макароны", 83.43, 5, "Италия")
        };
        getRareProduct(products, "Россия", 3);

    }

    public static void getRareProduct(Product[] products, String country, int count) {
        Arrays.stream(products)
                .filter(p -> p.getCountryOfOrigin().equals(country))
                .sorted(Comparator.comparing(Product::getQuantity))
                .limit(count)
                .sorted(Comparator.comparing(Product::getCost))
                .collect(Collectors.collectingAndThen(
                        Collectors.mapping(Product::getName, Collectors.toList()),
                        names -> String.format("%d редких продуктов на складе: %s;",
                                names.size(), String.join(", ", names))))
                .chars()
                .mapToObj(ch -> (char) ch).forEach(System.out::print);

//        /**
//         *    Оно работало, не заметил, когда сдавал
//         *    Т.к у меня всего 5 продуктов по России, все 5 и будут сортироваться 2 раза
//         *        если N = 5
//         * */
//        System.out.println();
//        System.out.println();
//        Arrays.stream(products)
//                .filter(p -> p.getCountryOfOrigin().equals(country))
//                .sorted(Comparator.comparing(Product::getQuantity))
//                .limit(count)
//                .sorted(Comparator.comparing(Product::getCost))
//                .forEach(System.out::println);

    }
}
