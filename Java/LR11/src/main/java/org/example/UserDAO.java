package org.example;

import lombok.*;
import org.example.model.*;
import org.hibernate.*;
import org.hibernate.cfg.Configuration;

import javax.persistence.Entity;
import java.util.List;

public class UserDAO {
    private SessionFactory sessionFactory;
    private final Configuration configuration = new Configuration();

    public void createTables(Class<?>... entities) {
        for (Class<?> entity : entities) {
            if (entity.isAnnotationPresent(Entity.class)) {
                configuration.addAnnotatedClass(entity);
            }
        }
        configuration.configure();
        sessionFactory = configuration.buildSessionFactory();
    }

    public void insert(Object... entities) {
        @Cleanup Session session = sessionFactory.openSession();

        for (Object entity : entities) {
            session.beginTransaction();

            if (entity.getClass() == Client.class) {
                String passport = ((Client) entity).getPassport();
                List clients = session.createQuery("FROM Client WHERE passport = :passport")
                        .setParameter("passport", passport)
                        .list();
                if (clients.isEmpty()) {
                    session.save(entity);
                    session.getTransaction().commit();
                } else {
                    System.out.println("\nОшибка добавления: Такой клиент уже есть\n");
                    session.getTransaction().rollback();
                }
            } else if (entity.getClass() == BankAccount.class) {
                if (((BankAccount) entity).getClient().getClientId() != null) {
                    session.save(entity);
                    session.getTransaction().commit();
                } else {
                    System.out.println("\nОшибка добавления: Нельзя создать счет несуществующему клиенту\n");
                    session.getTransaction().rollback();
                }
            } else if (entity.getClass() == Loan.class) {
                if (((Loan) entity).getBankAccount().getBankAccountId() != null) {
                    session.save(entity);
                    session.getTransaction().commit();
                } else {
                    System.out.println("\nОшибка добавления: Нельзя выдать кредит на несуществующий счет\n");
                    session.getTransaction().rollback();
                }
            } else {
                System.out.println("\nОшибка добавления: Такой сущности нет в базе данных\n");
                session.getTransaction().rollback();
            }
        }
    }

    public void update(Object... entities) {
        @Cleanup Session session = sessionFactory.openSession();
        session.beginTransaction();

        for (Object entity : entities) {
            if (entity instanceof IModel) {
                session.update(entity);
            } else {
                System.out.println("\nОшибка обновления: Такой сущности нет в базе данных\n");
            }
        }
        session.getTransaction().commit();

    }

    public void delete(Object... entities) {
        @Cleanup Session session = sessionFactory.openSession();

        session.beginTransaction();
        for (Object entity : entities) {
            switch (entity.getClass().getSimpleName()) {
                case "Client" -> {
                    if (session.find(Client.class, ((Client) entity).getClientId()) != null) {
                        Client client = session.find(Client.class, ((Client) entity).getClientId());
                        System.out.printf("\nКлиент #%d был удален\n\n", ((Client) entity).getClientId());
                        session.delete(client);
                    } else {
                        System.out.println("\nОшибка удаления: Такого клиента нет в базе данных\n");
                    }
                }
                case "BankAccount" -> {
                    if (((BankAccount) entity).getBankAccountId() != null) {
                        System.out.printf("\nБанковский счет #%d был удален\n\n", ((BankAccount) entity).getBankAccountId());
                        session.remove(entity);
                    } else {
                        System.out.println("\nОшибка удаления: Такого счета нет в базе данных\n");
                    }
                }
                case "Loan" -> {
                    if (((Loan) entity).getLoanId() != null) {
                        System.out.printf("\nКредит #%d был удален\n\n", ((Loan) entity).getLoanId());
                        session.delete(entity);
                    } else {
                        System.out.println("\nОшибка удаления: Такого кредита нет в базе данных\n");
                    }
                }
                default -> System.out.println("\nОшибка удаления: Такой сущности нет в базе данных\n");
            }
        }
        session.getTransaction().commit();
    }

//    public void getBankAccountsByClientId(long clientId) {
//        @Cleanup Session session = sessionFactory.openSession();
//        session.beginTransaction();
//
//        Client client = session.get(Client.class, clientId);
//        if (client != null && client.getBankAccountList() != null) {
//            List<BankAccount> bankAccountList = client.getBankAccountList();
//            if (!bankAccountList.isEmpty()) {
//                StringBuilder sb = new StringBuilder();
//                sb.append(String.format("\nБанковские счета клиента #%s:", clientId))
//                        .append(String.format("\n%-10s | %-15s | %-15s | %-13s | %-10s",
//                                "BankAccountID", "LastName", "FirstName", "Balance", "OpeningDate"))
//                        .append("\n")
//                        .append(String.format("%s+%s+%s+%s+%s", "--------------", "-----------------", "-----------------", "---------------", "-------------"));
//                System.out.println(sb);
//                for (BankAccount bankAccount : bankAccountList) {
//                    if (bankAccount != null) {
//                        System.out.printf("%-13s | %-15s | %-15s | %-13s | %-10s\n",
//                                bankAccount.getBankAccountId(), client.getLastName(),
//                                client.getFirstName(), bankAccount.getBalance(),
//                                bankAccount.getOpeningDate());
//                    }
//                }
//                System.out.println();
//            } else {
//                System.out.printf("\nУ клиента #%s нет счетов\n\n\n", clientId);
//            }
//        } else {
//            System.out.println("\nТакого клиента нет\n\n");
//        }
//        session.getTransaction().commit();
//
//    }
//
//
//    public void getLoansByClientId(long clientId) {
//        @Cleanup Session session = sessionFactory.openSession();
//        session.beginTransaction();
//
//        Client client = session.get(Client.class, clientId);
//        if (client != null && client.getBankAccountList() != null) {
//            List<BankAccount> bankAccountList = client.getBankAccountList();
//            if (!bankAccountList.isEmpty()) {
//                StringBuilder sb = new StringBuilder();
//                sb.append(String.format("\nКредиты клиента #%s:", clientId))
//                        .append(String.format("\n%-10s | %-6s | %-14s | %-10s | %-10s | %-10s | %-10s", "BankAccountID", "LoanId", "LoanAmount", "InterestRate", "LoanTerm", "DateOfGive", "DateOfTotalRepayment"))
//                        .append("\n")
//                        .append(String.format("%s+%s+%s+%s+%s+%s+%s", "--------------", "--------", "----------------", "--------------", "------------", "------------", "---------------------"))
//                        .append("\n");
//                int count = 0;
//                for (BankAccount bankAccount : bankAccountList) {
//                    if (bankAccount != null && bankAccount.getLoans() != null) {
//                        List<Loan> loanList = bankAccount.getLoans();
//                        for (Loan loan : loanList) {
//                            sb.append(String.format("%-13s | %-6s | %-14s | %-12s | %-10s | %-10s | %-10s\n", bankAccount.getBankAccountId(), loan.getLoanId(), loan.getLoanAmount(), loan.getInterestRate(), loan.getLoanTerm(), loan.getDateOfGive(), loan.getDateOfTotalRepayment()));
//                            count++;
//                        }
//                    }
//                }
//                if (count != 0) {
//                    System.out.println(sb);
//                } else {
//                    System.out.printf("\nУ клиента #%s нет кредитов\n\n\n", clientId);
//                }
//            } else {
//                System.out.printf("\nУ клиента #%s нет счетов\n\n\n", clientId);
//            }
//        } else {
//            System.out.println("\nТакого клиента нет\n\n");
//        }
//        session.getTransaction().commit();
//
//    }

    public void getAllClients() {
        @Cleanup Session session = sessionFactory.openSession();
        session.beginTransaction();

        List<Client> clients = session.createQuery("FROM Client", Client.class).list();
        if (!clients.isEmpty()) {
            System.out.println("\nСписок всех клиентов:");
            for (Client client: clients) {
                System.out.println(client);
            }
            System.out.println();


//            StringBuilder sb = new StringBuilder();
//            sb.append("\nСписок всех клиентов:")
//                    .append(String.format("\n%-6s | %-15s | %-16s | %-14s | %-10s | %-10s", "ClientID", "LastName", "FirstName", "Patronymic", "BirthDate", "Passport"))
//                    .append("\n")
//                    .append(String.format("%s+%s+%s+%s+%s+%s", "---------", "-----------------", "------------------", "----------------", "------------", "-------------"))
//                    .append("\n");
//            for (Client client : clients) {
//                if (client != null) {
//                    sb.append(String.format("%-8s | %-15s | %-16s | %-14s | %-8s | %-10s\n", client.getClientId(), client.getLastName(), client.getFirstName(), client.getPatronymic(), client.getBirthDate(), client.getPassport()));
//                }
//            }
//            System.out.println(sb);
        } else {
            System.out.println("\nКлиентов нет\n\n");
        }
        session.getTransaction().commit();
    }

    public void getAllBankAccounts() {
        @Cleanup Session session = sessionFactory.openSession();
        session.beginTransaction();

        List<BankAccount> bankAccounts = session.createQuery("FROM BankAccount", BankAccount.class).list();
        if (!bankAccounts.isEmpty()) {
            System.out.println("\nСписок всех банковских счетов:");
            for (BankAccount bankAccount: bankAccounts) {
                System.out.println(bankAccount);
            }
            System.out.println();

//            StringBuilder sb = new StringBuilder();
//            sb.append("\nСписок всех банковских счетов:")
//                    .append(String.format("\n%-10s | %-10s | %-10s | %-10s", "BankAccountID", "Balance", "OpeningDate", "ClosingDate"))
//                    .append("\n")
//                    .append(String.format("%s+%s+%s+%s", "--------------", "------------", "-------------", "-------------"))
//                    .append("\n");
//            for (BankAccount bankAccount : bankAccounts) {
//                if (bankAccount != null) {
//                    sb.append(String.format("%-13s | %-10s | %-11s | %-10s \n", bankAccount.getBankAccountId(), bankAccount.getBalance(), bankAccount.getOpeningDate(), bankAccount.getClosingDate()));
//                }
//            }
//            System.out.println(sb);
        } else {
            System.out.println("\nБанковских счетов нет\n\n");
        }
        session.getTransaction().commit();
    }

    public void getAllLoans() {
        @Cleanup Session session = sessionFactory.openSession();
        session.beginTransaction();

        List<Loan> loans = session.createQuery("FROM Loan ", Loan.class).list();
        if (!loans.isEmpty()) {
            System.out.println("\nСписок всех кредитов:");
            for (Loan loan : loans) {
                System.out.println(loan);
            }
            System.out.println();

//            StringBuilder sb = new StringBuilder();
//            sb.append("\nСписок всех кредитов:")
//                    .append(String.format("\n%-10s | %-6s | %-10s | %-10s | %-8s | %-10s | %-10s", "BankAccountID", "LoanID", "LoanAmount", "InterestRate", "LoanTerm", "DateOfGive", "DateOfTotalRepayment"))
//                    .append("\n")
//                    .append(String.format("%s+%s+%s+%s+%s+%s+%s", "--------------", "--------", "------------", "--------------", "----------", "------------", "---------------------"))
//                    .append("\n");
//            for (Loan loan : loans) {
//                if (loan != null) {
//                    sb.append(String.format("%-13s | %-6s | %-10s | %-12s | %-8s | %-10s | %-10s \n", loan.getBankAccount().getBankAccountId(), loan.getLoanId(), loan.getLoanAmount(), loan.getInterestRate(), loan.getLoanTerm(), loan.getDateOfGive(), loan.getDateOfTotalRepayment()));
//                }
//            }
//            System.out.println(sb);
        } else {
            System.out.println("\nКредитов нет\n\n");
        }
        session.getTransaction().commit();
    }

    public void closeSessionFactory() {
        sessionFactory.close();
    }


    public void getBankAccountsByClientName(String name) {

        @Cleanup Session session = sessionFactory.openSession();
        List<Client> clients = session.createQuery("FROM Client WHERE firstName = :name")
                .setParameter("name", name).list();

        for (Client client : clients) {
            session.beginTransaction();

            if (client != null && client.getBankAccountList() != null) {
                System.out.printf("\nБанковские счета клиента %s (id: %d):\n", name, client.getClientId());
                System.out.println(client + "\n");
            } else {
                System.out.println("\nТакого клиента нет\n\n");
            }
            session.getTransaction().commit();
        }
    }

    public void getLoansByClientName(String name) {
        @Cleanup Session session = sessionFactory.openSession();
        List<Client> clients = session.createQuery("FROM Client WHERE firstName =: name")
                .setParameter("name", name).list();

        for (Client client : clients) {
            session.beginTransaction();

            if (client != null && client.getBankAccountList() != null) {
                List<BankAccount> bankAccountList = client.getBankAccountList();
                System.out.printf("\nКредиты клиента %s (id: %d):\n", name, client.getClientId());
                if (!bankAccountList.isEmpty()) {
                    for (BankAccount bankAccount : bankAccountList) {
                        if (bankAccount != null && bankAccount.getLoans() != null) {
                            System.out.println(bankAccount.toString1());
                        }
                    }
                } else {
                    System.out.printf("\nУ клиента #%s нет счетов\n\n\n", client.getClientId());
                }
            } else {
                System.out.println("\nТакого клиента нет\n\n");
            }
            session.getTransaction().commit();
        }
    }
}