package org.example.components;

import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

@Component
public class ProductService {
    private final List<Product> productList = new ArrayList<>();

    @PostConstruct
    private void initMethod() {
        productList.add(new Product(1, "Молоко", 54.00));
        productList.add(new Product(2, "Бананы", 124.00));
        productList.add(new Product(3, "Яблоки", 34.54));
        productList.add(new Product(4, "Макароны", 74.11));
        productList.add(new Product(5, "Вишневый сок", 78.50));
        productList.add(new Product(6, "Торт", 234.10));
        productList.add(new Product(7, "Яйца", 67.99));
        productList.add(new Product(8, "Вода", 26.50));
        productList.add(new Product(9, "Чипсы", 96.43));
        productList.add(new Product(10, "Хлеб", 18.10));
    }

    public void printAll() {
        for (Product product : productList) {
            System.out.println(product);
        }
    }

    public Product findByTitle(String title) {
        for (Product product : productList) {
            if (product.getTitle() == title) {
                return product;
            }
        }
        System.out.printf("Товара \"%s\" нет\n\n", title);
        return null;
    }
}
