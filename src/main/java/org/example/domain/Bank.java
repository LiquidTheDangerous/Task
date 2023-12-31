package org.example.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


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
}
