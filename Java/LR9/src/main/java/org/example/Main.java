package org.example;

import java.sql.*;

public class Main {
    public static void main(String[] args) throws IllegalAccessException {
        Kangaroo[] kangaroos = {
                new Kangaroo("Kenga", 14, 50, 5, "Трава и фрукты", Type.LAGOSTROPUS),
                new Kangaroo("Даниил", 6, 15, 0, "Трава", Type.MACROPUS),
                new Kangaroo("Джимбо", 19, 23, 2, "Трава и фрукты", Type.OSPHRANTER),
                new Kangaroo("Немо", 1, 5, 0, "Молоко", Type.MACROPUS)
        };

        TableChanger tableChanger = new TableChanger();

        tableChanger.connection(
                "jdbc:sqlite:jdbc.db"
        );

        tableChanger.createTable(Kangaroo.class);
        for (Kangaroo kangaroo : kangaroos) {
            tableChanger.insert(kangaroo);
        }

//        tableChanger.delete(kangaroos[0]);
//        tableChanger.delete(kangaroos[1]);
//        tableChanger.delete(kangaroos[2]);


//        tableChanger.clearTable(Kangaroo.class);

//        tableChanger.dropTable(Kangaroo.class);

//        tableChanger.select(Kangaroo.class, "SELECT * FROM people;");

        tableChanger.printScript();

        tableChanger.closeConn();
    }
}
