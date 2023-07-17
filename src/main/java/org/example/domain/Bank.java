package org.example.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name="bank",schema = "public")
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class Bank {
    @Id
    @Column(name="id", nullable = false)
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;

    @Column(name="name", nullable = false, unique = true)
    private String name;

    @Column(name="bik_code", nullable = false, unique = true)
    private Integer bikCode;

    @OneToMany(mappedBy = "bank",fetch = FetchType.EAGER)
    @JsonBackReference
    private List<Deposit> deposits;
}
