package org.example.model;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Client implements IModel {
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
    private LocalDate birthDate;

    @Column(unique = true)
    private String passport;

    @OneToMany(mappedBy = "client", cascade = CascadeType.ALL/*, orphanRemoval = true*/)
    private List<BankAccount> bankAccountList;

    public Client(String lastName, String firstName, String patronymic, LocalDate birthDate, String passport) {
        this.lastName = lastName;
        this.firstName = firstName;
        this.patronymic = patronymic;
        this.birthDate = birthDate;
        this.passport = passport;
        bankAccountList = new ArrayList<>();
    }


    public String toString1() {
        return "Client{" +
                "clientId=" + clientId +
                ", passport='" + passport + '\'' +
                ", bankAccountList=" + bankAccountList +
                '}';
    }

    @Override
    public String toString() {
        return "Client{" +
                "clientId=" + clientId +
                ", lastName='" + lastName + '\'' +
                ", firstName='" + firstName + '\'' +
                ", patronymic='" + patronymic + '\'' +
                ", birthDate=" + birthDate +
                ", passport='" + passport + '\'' +
                '}';
    }
}
