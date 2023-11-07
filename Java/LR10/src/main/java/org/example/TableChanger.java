package org.example;

import java.lang.reflect.Field;
import java.sql.*;

import static java.sql.DriverManager.getConnection;

public class TableChanger {
    private Connection conn;
    private static StringBuilder script = new StringBuilder();

    public void connection(String url, String user, String pass) {
        try {
            conn = getConnection(url, user, pass);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void createTable() {
        StringBuilder query = new StringBuilder("DROP SCHEMA lr_10_java CASCADE; CREATE SCHEMA lr_10_java;");
        query.append("CREATE TABLE LR_10_java.Student\n" +
                "(\n" +
                "    student_id      BIGSERIAL    NOT NULL PRIMARY KEY,\n" +
                "    student_name    VARCHAR(100) NOT NULL,\n" +
                "    passport_series VARCHAR(4)   NOT NULL,\n" +
                "    passport_number VARCHAR(6)   NOT NULL,\n" +
                "    UNIQUE (passport_series, passport_number)\n" +
                ");");
        query.append("CREATE TABLE LR_10_java.Subject\n" +
                "(\n" +
                "    subject_id   BIGSERIAL    NOT NULL PRIMARY KEY,\n" +
                "    subject_name VARCHAR(100) NOT NULL\n" +
                ");");
        query.append("CREATE TABLE LR_10_java.Progress\n" +
                "(\n" +
                "    progress_id BIGSERIAL NOT NULL PRIMARY KEY,\n" +
                "    student_id  BIGINT    NOT NULL REFERENCES  LR_10_java.Student(student_id),\n" +
                "    subject_id  BIGINT    NOT NULL REFERENCES  LR_10_java.Subject(subject_id),\n" +
                "    assessment  INTEGER   NOT NULL\n" +
                ");");
        executeUpdate(query);
    }

    public void addConstraintPassport() {
        StringBuilder query = new StringBuilder(
                "ALTER TABLE LR_10_java.Student\n" +
                        "ADD CONSTRAINT unique_passport\n" +
                        "UNIQUE (passport_series, passport_number);");

        executeUpdate(query);
    }

    public void addConstraintAssessment() {
        StringBuilder query = new StringBuilder(
                "ALTER TABLE LR_10_java.Progress\n" +
                        "    ADD CONSTRAINT check_assessment\n" +
                        "        CHECK (assessment >= 2 AND assessment <= 5);");

        executeUpdate(query);
    }

    public void insertData() {
        StringBuilder query = new StringBuilder(
                "INSERT INTO LR_10_java.Student(student_name, passport_series, passport_number)\n" +
                        "VALUES ('Егор', '5436', '234254'),\n" +
                        "       ('Даниил', '2342', '345346'),\n" +
                        "       ('Дмитрий', '4534', '853452'),\n" +
                        "       ('Михаил', '3954', '953454'),\n" +
                        "       ('Алексей', '5843', '459345');\n" +
                        "\n" +
                        "INSERT INTO LR_10_java.Subject (subject_name)\n" +
                        "VALUES ('Математика'),\n" +
                        "       ('Русский язык'),\n" +
                        "       ('История'),\n" +
                        "       ('Английский язык');\n" +
                        "\n" +
                        "INSERT INTO LR_10_java.Progress (student_id, subject_id, assessment)\n" +
                        "VALUES (1, 2, 4),\n" +
                        "       (2, 2, 4),\n" +
                        "       (1, 1, 2),\n" +
                        "       (3, 4, 5),\n" +
                        "       (3, 1, 3),\n" +
                        "       (4, 2, 4);");

        executeUpdate(query);
    }

    public void select1(String subject) throws SQLException {
        StringBuilder query = new StringBuilder("" +
                "SELECT s.*\n" +
                "FROM LR_10_java.Progress p\n" +
                "         JOIN LR_10_java.Student s ON p.student_id = s.student_id\n" +
                "         JOIN LR_10_java.Subject sb ON p.subject_id = sb.subject_id\n" +
                "WHERE sb.subject_name = '" + subject + "' AND p.assessment > 3;");

        PreparedStatement order = conn.prepareStatement(query.toString());
        ResultSet res = order.executeQuery();
        System.out.println("\nВывести список студентов, сдавших определенный предмет, на оценку выше 3:");
        while (res.next()) {
            String student_id = res.getString(1);
            String student_name = res.getString(2);
            String passport_series = res.getString(3);
            String passport_number = res.getString(4);

            System.out.println(student_id + " | "
                    + student_name + " | "
                    + passport_series + " | "
                    + passport_number);
        }
    }

    public void addConstraintDeleteStudent() throws SQLException {
        StringBuilder query = new StringBuilder(
                "ALTER TABLE LR_10_java.Progress\n" +
                        "    ADD CONSTRAINT fk_student\n" +
                        "        FOREIGN KEY (student_id)\n" +
                        "            REFERENCES LR_10_java.Student (student_id)\n" +
                        "            ON DELETE CASCADE;");

        executeUpdate(query);
    }

    public void select2(String subject) throws SQLException {
        StringBuilder query = new StringBuilder(
                "SELECT sb.subject_name, AVG(assessment)\n" +
                        "FROM LR_10_java.Progress p\n" +
                        "JOIN LR_10_java.Subject sb ON p.subject_id = sb.subject_id\n" +
                        "WHERE  sb.subject_name = '"+ subject + "'\n" +
                        "group by sb.subject_name;");

        PreparedStatement order = conn.prepareStatement(query.toString());
        ResultSet res = order.executeQuery();

        System.out.println("\nПосчитать средний балл по определенному предмету:");
        while (res.next()) {
            String subject_name = res.getString(1);
            String avg = res.getString(2);

            System.out.println(subject_name + " | " + avg);
        }
    }

    public void select3(String student_name) throws SQLException {
        StringBuilder query = new StringBuilder(
                "SELECT s.student_name, AVG(assessment)\n" +
                        "FROM LR_10_java.Progress p\n" +
                        "JOIN LR_10_java.Student s ON p.student_id = s.student_id\n" +
                        "WHERE  s.student_name = '" + student_name + "'\n" +
                        "group by s.student_name;");

        PreparedStatement order = conn.prepareStatement(query.toString());
        ResultSet res = order.executeQuery();
        System.out.println("\nПосчитать средний бал по определенному студенту:");
        while (res.next()) {
            String subject_name = res.getString(1);
            String avg = res.getString(2);

            System.out.println(subject_name + " | " + avg);
        }
    }

    public void select4() throws SQLException {
        StringBuilder query = new StringBuilder(
                "SELECT sb.subject_name, COUNT(sb.subject_name)\n" +
                        "FROM LR_10_java.Progress p\n" +
                        "JOIN LR_10_java.Student s ON p.student_id = s.student_id\n" +
                        "JOIN LR_10_java.Subject sb ON p.subject_id = sb.subject_id\n" +
                        "GROUP BY sb.subject_name\n" +
                        "ORDER BY COUNT(sb.subject_name) DESC\n" +
                        "LIMIT 3;\n");

        PreparedStatement order = conn.prepareStatement(query.toString());
        ResultSet res = order.executeQuery();
        System.out.println("\nНайти три предмета, которые сдали наибольшее количество студентов:");
        while (res.next()) {
            String subject_name = res.getString(1);
            String count = res.getString(2);

            System.out.println(subject_name + " | " + count);
        }
    }


    public void closeConn() {
        try {
            conn.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void executeUpdate(StringBuilder query) {
        try (Statement statement = conn.createStatement()) {
            script.append(query).append("\n");
            statement.executeUpdate(query.toString());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
