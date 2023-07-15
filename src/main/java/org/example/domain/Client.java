package org.example.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.util.List;


@Entity
@Table(name="client", schema = "public")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Client {
    @Id
    @Column(name="id",nullable = false)
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;

    @Column(name="name", nullable = false)
    private String name;

    @Column(name="shortname")
    private String shortname;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="organizational_legal_form_id")
    private OrganizationalLegalForm organizationalLegalForm;

    @OneToMany(mappedBy = "client", fetch = FetchType.LAZY)
    private List<Deposit> deposits;
}
