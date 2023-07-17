package org.example.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;




@Entity
@Table(name="client", schema = "public")
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class Client {
    @Id
    @Column(name="id",nullable = false)
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;

    @Column(name="name", nullable = false, unique = true)
    private String name;

    @Column(name="shortname")
    private String shortname;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="organizational_legal_form_id")
    private OrganizationalLegalForm organizationalLegalForm;
}
