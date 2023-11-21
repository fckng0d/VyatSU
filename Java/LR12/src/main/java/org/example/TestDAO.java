package org.example;

import lombok.*;
import org.example.model.*;
import org.hibernate.*;
import org.hibernate.cfg.Configuration;

import javax.persistence.Entity;
import javax.persistence.PersistenceException;
import java.util.List;
import java.util.Random;

public class TestDAO {
    private static SessionFactory sessionFactory;
    private final Configuration configuration = new Configuration();
    private static final Random random = new Random();
    private static final int countOfEntries = 40;   // Количество записей
    private static final int countOfThreads = 8;    // Количество потоков
    private static final int countOfEntriesForEachThread = 20000;   // Количество операций для каждого потока

    private static volatile int rollbacksCount = 0;

    public void createTables(Class<?>... entities) {
        for (Class<?> entity : entities) {
            if (entity.isAnnotationPresent(Entity.class)) {
                configuration.addAnnotatedClass(entity);
            }
        }
        configuration.configure();
        configuration.setProperty("hibernate.connection.pool_size", String.valueOf(countOfThreads));
        sessionFactory = configuration.buildSessionFactory();
    }

    public void fillItems() {
        @Cleanup Session session = sessionFactory.openSession();
        for (int i = 0; i < countOfEntries; i++) {
            Transaction transaction = session.beginTransaction();
            session.save(new Item());
            transaction.commit();
        }
    }

    @SneakyThrows
    public void testForItems() {
        System.out.printf("\nПотоков: %d\nЗаписей: %d\nОпераций для каждого потока: %d\n\n",
                countOfThreads, countOfEntries, countOfEntriesForEachThread);
        long start = System.currentTimeMillis();

        Thread[] threads = new Thread[countOfThreads];
        for (int i = 0; i < countOfThreads; i++) {
            threads[i] = new Thread(() -> incrementValue(sessionFactory));
            threads[i].start();
            System.out.printf("Thread #%d started...\n", i + 1);
        }

        for (Thread thread : threads) {
            thread.join();
        }

        @Cleanup Session session = sessionFactory.openSession();
//        List<Item> items = session.createQuery("FROM Item", Item.class).list();
//        int sum = items.stream().mapToInt(Item::getVal).sum();
        Object sum = session.createQuery("SELECT SUM(val) FROM Item").getSingleResult();

        long end = System.currentTimeMillis();

        System.out.println("End.\n");
        System.out.printf("Сумма всех val = %d\n", ((Number) sum).intValue());
        System.out.printf("Количество откатов = %d\n", rollbacksCount);
        System.out.printf("Time = %.2fmin\n", ((double) end - start) / 60000);

        sessionFactory.close();
    }

    @SneakyThrows
    private static void incrementValue(SessionFactory sessionFactory) {
        long itemId = 0;
        boolean success = true;
        for (int i = 0; i < countOfEntriesForEachThread; i++) {
            // после отката не обновляем id, иначе это по сути будет совсем другая транзакция
            if (success) {
                itemId = (long) random.nextInt(countOfEntries) + 1;
            }
            @Cleanup Session session = sessionFactory.getCurrentSession();
            Transaction transaction = session.beginTransaction();
            try {
                Item item = session.get(Item.class, itemId);
                item.setVal(item.getVal() + 1);
                Thread.sleep(5);

                session.save(item);

                success = true;
                transaction.commit();
            } catch (PersistenceException e) {
                System.out.println(Thread.currentThread().getName() + " rollback");
                rollbacksCount++;
                success = false;
                i--;
                transaction.rollback();
            } finally {
                session.close();
            }
        }
    }
}
