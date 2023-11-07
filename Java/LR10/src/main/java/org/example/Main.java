package org.example;

import java.sql.SQLException;

public class Main {
    public static void main(String[] args) throws SQLException {
        TableChanger tableChanger = new TableChanger();

        tableChanger.connection(
                "jdbc:postgresql://localhost:5432/postgres",
                "postgres",
                "Acid0101/J/q_p"
        );

        tableChanger.createTable();

        tableChanger.addConstraintPassport();
        tableChanger.addConstraintAssessment();
        tableChanger.addConstraintDeleteStudent();

        tableChanger.insertData();

        tableChanger.select1("Русский язык");
        tableChanger.select2("Математика");
        tableChanger.select3("Егор");
        tableChanger.select4();
    }
}