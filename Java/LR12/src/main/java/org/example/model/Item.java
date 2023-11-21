package org.example.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
public class Item {
    @Version
    @Getter
    private long version;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter
    @Setter
    private Long id;

    @Getter
    @Setter
    private Integer val = 0;

    public Item() {
        this.val = 0;
    }
}
