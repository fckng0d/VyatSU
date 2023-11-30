package org.example.model;

import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@Entity
public class Loan implements IModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "loan_id")
    private Long loanId;

    @Column(name = "loan_amount")
    private Double loanAmount;

    @Column(name = "interestRate")
    private Double interestRate;

    @Column(name = "loan_term")
    private Integer loanTerm;

    @Column(name = "date_of_give")
    private LocalDate dateOfGive;

    @Column(name = "date_of_total_repayment")
    private LocalDate dateOfTotalRepayment;

    @ManyToOne
    @JoinColumn(name = "bank_account_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private BankAccount bankAccount;

    public Loan(Double loanAmount, Double interestRate, Integer loanTerm, LocalDate dateOfGive, BankAccount bankAccount) {
        this.loanAmount = loanAmount;
        this.interestRate = interestRate;
        this.loanTerm = loanTerm;
        this.dateOfGive = dateOfGive;
        this.bankAccount = bankAccount;
    }

    @Override
    public String toString() {
        return "Loan{" +
                "loanId=" + loanId +
                ", loanAmount=" + loanAmount +
                ", interestRate=" + interestRate +
                ", loanTerm=" + loanTerm +
                ", dateOfGive=" + dateOfGive +
                ", dateOfTotalRepayment=" + dateOfTotalRepayment +
                '}';
    }
}
