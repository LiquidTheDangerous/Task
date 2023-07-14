package org.example.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Set;

@Entity
@Table(name="client", schema = "public")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Client {
    @Id
    @Column(name="id")
    private Long id;

    @Column(name="name")
    private String name;

    @Column(name="shortname")
    private String nickname;

    @OneToOne
    private OrganizationalLegalForm organizationalLegalForm;

    @OneToMany(mappedBy = "client",fetch = FetchType.LAZY)
    private Set<Deposit> deposits;


}
