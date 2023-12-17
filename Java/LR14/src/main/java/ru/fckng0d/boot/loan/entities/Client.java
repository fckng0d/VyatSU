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

    @OneToOne
    @JoinColumn(name = "username")
    private User user;

    @OneToMany(mappedBy = "client", cascade = CascadeType.ALL/*, orphanRemoval = true*/)
    private List<Loan> loanList;
}

