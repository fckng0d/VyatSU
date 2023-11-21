package org.example;

import org.example.model.Item;

public class Main {
    public static void main(String[] args) {
        TestDAO testDAO = new TestDAO();

        testDAO.createTables(Item.class);
        testDAO.fillItems();

        testDAO.testForItems();
    }
}
