package ru.fckng0d.boot.loan.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@Entity
public class Client {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "client_id")
    private Long clientId;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "first_name")
    private String firstName;

    private String patronymic;

    @Column(name = "birth_date")
    private String birthDate;

    @Column(unique = true)
    private String passport;

    @OneToMany(mappedBy = "client", cascade = CascadeType.ALL/*, orphanRemoval = true*/)
    private List<Loan> loanList;


    public Client(String lastName, String firstName, String patronymic, String birthDate, String passport) {
        this.lastName = lastName;
        this.firstName = firstName;
        this.patronymic = patronymic;
        this.birthDate = birthDate;
        this.passport = passport;
        loanList = new ArrayList<>();
    }


//    public String toString1() {
//        return "Client{" +
//                "clientId=" + clientId +
//                ", passport='" + passport + '\'' +
//                ", bankAccountList=" + bankAccountList +
//                '}';
//    }
//
//    @Override
//    public String toString() {
//        return "Client{" +
//                "clientId=" + clientId +
//                ", lastName='" + lastName + '\'' +
//                ", firstName='" + firstName + '\'' +
//                ", patronymic='" + patronymic + '\'' +
//                ", birthDate=" + birthDate +
//                ", passport='" + passport + '\'' +
//                '}';
//    }
}

