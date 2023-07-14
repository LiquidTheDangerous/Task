package org.example.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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
    @JoinColumn(name="legal_form", referencedColumnName = "id")
    OrganizationalLegalForm organizationalLegalForm;


}
