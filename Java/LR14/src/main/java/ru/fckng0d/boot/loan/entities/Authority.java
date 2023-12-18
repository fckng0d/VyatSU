package ru.fckng0d.boot.loan.entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@NoArgsConstructor
@Entity
@Table(name = "authorities")
public class Authority {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "auth_id")
    private Long id;

    @Column(insertable=false, updatable=false)
    private String username;

    @Column(name = "authority")
    private String role;

    @ManyToOne
    @JoinColumn(name = "username", foreignKey = @ForeignKey(name = "username"))
    private User user;
}
