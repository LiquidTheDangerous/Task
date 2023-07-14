package org.example.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Entity
@Table(name="bank")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Bank {
    @Id
    @Column(name="id")
    private Long id;

    @Column(name="name")
    private String name;

    @Column(name="bik_code")
    private Long bikCode;

    @OneToMany(mappedBy = "bank",fetch = FetchType.LAZY)
    private Set<Deposit> deposits;
}
