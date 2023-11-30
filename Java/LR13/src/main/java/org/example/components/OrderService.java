package org.example.components;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
public class OrderService {
//    @Autowired
    private Cart cart;

    private static int id = 1;

    public void createOrder(Cart cart) {
        System.out.printf("=== Заказ #%d ===\n", id);
        id++;
        double totalCost = 0;
        for (Product product : cart.getPurchases()) {
            System.out.println(product);
            totalCost += product.getCost();
        }
        System.out.printf("Общая стоимость заказа: %.2f\n\n", totalCost);
    }
}
