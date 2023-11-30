package org.example.components;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@Scope("prototype")
public class Cart {
    private List<Product> purchases = new ArrayList<>();

    public void add(Product product) {
        if (product != null) {
            purchases.add(product);
        }
    }

    public List<Product> getPurchases() {
        return purchases;
    }
}
