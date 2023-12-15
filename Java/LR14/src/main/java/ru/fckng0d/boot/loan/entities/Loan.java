package ru.fckng0d.boot.loan.entities;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@Entity
public class Loan {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "loan_id")
    private Long loanId;

    @Column(name = "loan_amount")
    private Double loanAmount;

    @Column(name = "interest_rate")
    private Double interestRate;

    @Column(name = "loan_term")
    private Integer loanTerm;

    @Column(name = "date_of_give")
    private String dateOfGive;

    @Column(name = "date_of_total_repayment")
    private String dateOfTotalRepayment;

    @ManyToOne
    @JoinColumn(name = "client_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Client client;

    public Loan(Double loanAmount, Double interestRate, Integer loanTerm, String dateOfGive, Client client) {
        this.loanAmount = loanAmount;
        this.interestRate = interestRate;
        this.loanTerm = loanTerm;
        this.dateOfGive = dateOfGive;
        this.client = client;
    }

//    @Override
//    public String toString() {
//        return "Loan{" +
//                "loanId=" + loanId +
//                ", loanAmount=" + loanAmount +
//                ", interestRate=" + interestRate +
//                ", loanTerm=" + loanTerm +
//                ", dateOfGive=" + dateOfGive +
//                ", dateOfTotalRepayment=" + dateOfTotalRepayment +
//                '}';
//    }
}

