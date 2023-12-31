package ru.fckng0d.boot.loan.entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Data
@NoArgsConstructor
@Entity
@Table(name = "user_t")
public class User {
    @Id
    @Column(name = "username")
    private String username;

    @Column(name = "real_name")
    private String realName;

    @Column
    private String password;

    @OneToOne(mappedBy = "user")
    @JoinColumn(name = "username")
    private Client client;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "user")
    private List<Authority> authorityList;
}
