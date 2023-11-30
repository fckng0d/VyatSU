package org.example.model;

import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@Entity
@Table(name = "bank_account")
public class BankAccount implements IModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "bank_account_id")
    private Long bankAccountId;

    private Double balance;

    @Column(name = "opening_date")
    private LocalDate openingDate;

    @Column(name = "closing_date", nullable = true)
    private LocalDate closingDate;

    @ManyToOne
    @JoinColumn(name = "client_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Client client;

    @OneToMany(mappedBy = "bankAccount", cascade = CascadeType.ALL/*, orphanRemoval = true*/)
    private List<Loan> loans;

    public BankAccount(Double balance, LocalDate openingDate, Client client) {
        this.balance = balance;
        this.openingDate = openingDate;
        this.client = client;
        loans = new ArrayList<>();
    }

    @Override
    public String toString() {
        return "BankAccount{" +
                "bankAccountId=" + bankAccountId +
                ", balance=" + balance +
                ", openingDate=" + openingDate +
                '}';
    }

    public String toString1() {
        return "BankAccount{" +
                "bankAccountId=" + bankAccountId +
                ", loans=" + loans +
                '}';
    }
}
