package org.example.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="organizational_legal_form", schema = "public")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class OrganizationalLegalForm {
    @Id
    @Column(name="id", nullable = false)
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    @Column(name="name", nullable = false)
    private String name;

    @Column(name="short_name", nullable = false)
    private String shortName;
}
