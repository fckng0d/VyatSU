package org.example;

import org.example.model.BankAccount;
import org.example.model.Client;
import org.example.model.Loan;

import java.time.LocalDate;

public class Main {
    public static void main(String[] args) {
        UserDAO userDAO = new UserDAO();
        userDAO.createTables(Client.class, BankAccount.class, Loan.class);

        Client[] clients = {
                new Client("Русаков", "Егораыва", "Алексеевич", LocalDate.of(2004, 9, 18), "5321594305"),
                /** Если такой пользователь (по паспорту) уже есть в базе, то он не добавится,
                        как и все счета и кредиты, которые ссылаются на него */
//                new Client("Русаков", "Егораыва", "Алексеевич", LocalDate.of(2004, 9, 18), "5321594305"),
                new Client("Коковихин", "Данил", "Константинович", LocalDate.of(2003, 12, 30), "3245753234"),
                new Client("Летягин", "Дмитрий", "Борисович", LocalDate.of(2002, 3, 11), "4953293054"),
                new Client("Фыааыва", "Егор", "Борисович", LocalDate.of(2004, 9, 18), "1321594305")
        };

        BankAccount[] bankAccounts = {
                new BankAccount(1242.99, LocalDate.of(2020, 10, 11), clients[0]),
                new BankAccount(543234.00, LocalDate.of(2019, 2, 4), clients[0]),
                new BankAccount(123.43, LocalDate.of(2018, 12, 2), clients[1]),
                new BankAccount(5000.00, LocalDate.of(2022, 7, 31), clients[2]),
                new BankAccount(0.00, LocalDate.of(2023, 12, 31), clients[3]),
        };

        Loan[] loans = {
                new Loan(10400.00, 7.8, 12, LocalDate.of(2022, 4, 12), bankAccounts[0]),
                new Loan(2032000.00, 5.8, 36, LocalDate.of(2023, 11, 1), bankAccounts[1]),
                new Loan(15000.00, 7.2, 6, LocalDate.of(2023, 2, 14), bankAccounts[2]),
                new Loan(2100.00, 9.2, 6, LocalDate.of(2022, 6, 23), bankAccounts[2]),
                new Loan(213200.00, 9.2, 6, LocalDate.of(2023, 12, 31), bankAccounts[4])
        };

        userDAO.insert((Object[]) clients);
        userDAO.insert((Object[]) bankAccounts);
        userDAO.insert((Object[]) loans);

        /** Нельзя добавить клиентов с одинаковым паспортом */
//        userDAO.insert(clients[0]);


        clients[0].setFirstName("Егор");
        clients[1].setFirstName("Даниил");
        loans[0].setLoanAmount(loans[0].getLoanAmount() * 3);
        userDAO.update(clients[0], clients[1], loans[0]);


//        userDAO.delete(clients[1]);
        /** Нельзя удалить сущность, которая уже была удалена*/
//        userDAO.delete(clients[1]);


        /** Нельзя добавлять/обновлять/удалять несуществующие сущности */
//        Object object = new Object();
//        userDAO.insert(object);
//        userDAO.update(object);
//        userDAO.delete(object);


//        userDAO.getBankAccountsByClientId(1);
//        System.out.println("\n====================================================\n");

        userDAO.getBankAccountsByClientName("Егор");
//        System.out.println("\n====================================================\n");
//
//        userDAO.getLoansByClientId(1);
//        System.out.println("\n====================================================\n");

        userDAO.getLoansByClientName("Егор");
//        System.out.println("\n====================================================\n");


        userDAO.getAllClients();
        userDAO.getAllBankAccounts();
        userDAO.getAllLoans();


        userDAO.closeSessionFactory();
    }
}