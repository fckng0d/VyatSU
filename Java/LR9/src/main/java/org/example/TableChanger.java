package org.example;

import java.lang.reflect.Field;
import java.sql.*;

public class TableChanger {
    private Connection conn;
    private static StringBuilder script = new StringBuilder();

    public void connection(String url) {
        try {
//            DriverManager.registerDriver(new JDBC());
            conn = DriverManager.getConnection(url);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void createTable(Class<?> someClass) throws IllegalAccessException {
        if (someClass.isAnnotationPresent(Table.class)) {
            Table tableAnnotation = someClass.getAnnotation(Table.class);
            String tableName = tableAnnotation.title();

            StringBuilder query = new StringBuilder("DROP TABLE IF EXISTS " + tableName +
                     "; CREATE TABLE ");
            query.append(tableName);
            query.append(" (");

            Field[] fields = someClass.getDeclaredFields();
            int skipCount = 0;
            for (Field field : fields) {
                if (field.isAnnotationPresent(Column.class)) {
                    Column columnAnnotation = field.getAnnotation(Column.class);
                    field.setAccessible(true);
                    query.append(field.getName());
                    if (field.getType() == int.class) {
                        query.append(" INTEGET").append(", ");
                    } else if (field.getType() == String.class) {
                        query.append(" VARCHAR(100)").append(", ");
                    } else if (field.getType().isEnum()) {
                        query.append(" VARCHAR(100)").append(", ");
                    }
                } else {
                    skipCount++;
                }
            }
            query.setLength(query.length() - 2);
            query.append(")");

            executeUpdate(query);
            script.append("\n");
        }
    }

    public void insert(Object object) throws IllegalAccessException {
        if (object.getClass().isAnnotationPresent(Table.class)) {
            Class<?> objectClass = object.getClass();

            String tableName = objectClass.getAnnotation(Table.class).title();

            Field[] objectFields = objectClass.getDeclaredFields();
            StringBuilder query = new StringBuilder("INSERT INTO ");
            query.append(tableName).append(" VALUES (");

            int indexFirstAnnotatedField = 0;
            for (Field field : objectFields) {
                if (field.isAnnotationPresent(Column.class)) {
                    field.setAccessible(true);
                    if (field.getType() == int.class) {
                        query.append(field.get(object)).append(", ");
                    } else if (field.getType() == String.class) {
                        query.append("'").append(field.get(object)).append("', ");
                    } else if (field.getType() == Type.class) {
                        query.append("'").append(field.get(object)).append("', ");
                    }
                } else {
                    indexFirstAnnotatedField++;
                }
            }
            query.setLength(query.length() - 2);
            query.append(")");

            executeUpdate(query);
        }
    }

    public void delete(Object object) throws IllegalAccessException {
        Class<?> objectClass = object.getClass();

        String tableName = objectClass.getAnnotation(Table.class).title();
        Field[] objectFields = objectClass.getDeclaredFields();
        StringBuilder query = new StringBuilder("DELETE FROM ");
        for (Field field : objectFields) {
            if (field.isAnnotationPresent(Column.class)) {
                field.setAccessible(true);
                query.append(tableName);
                query.append(" WHERE ").append(field.getName());
                query.append(" = ").append(field.get(object));
                break;
            }
        }

        executeUpdate(query);
    }

    public void clearTable(Class<?> someClass) {
        StringBuilder query = new StringBuilder("DELETE FROM ");
        query.append(someClass.getAnnotation(Table.class).title());

        executeUpdate(query);
    }

    public void dropTable(Class<?> someClass) {
        StringBuilder query = new StringBuilder("DROP TABLE ");
        query.append(someClass.getAnnotation(Table.class).title());

        executeUpdate(query);
    }

    public void printScript() {
        System.out.println(script.toString());
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
