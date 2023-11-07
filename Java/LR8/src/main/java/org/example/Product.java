package org.example;

public class Product {
    private String name;
    private double cost;
    private int quantity;
    private String countryOfOrigin;

    public Product(String name, double cost, int quantity, String countryOfOrigin) {
        this.name = name;
        this.cost = cost;
        this.quantity = quantity;
        this.countryOfOrigin = countryOfOrigin;
    }

    public String getName() {
        return name;
    }

    public double getCost() {
        return cost;
    }

    public int getQuantity() {
        return quantity;
    }

    public String getCountryOfOrigin() {
        return countryOfOrigin;
    }

    @Override
    public String toString() {
        return "Product{" +
                "name='" + name + '\'' +
                ", cost=" + cost +
                ", quantity=" + quantity +
                ", countryOfOrigin='" + countryOfOrigin + '\'' +
                '}';
    }
}
