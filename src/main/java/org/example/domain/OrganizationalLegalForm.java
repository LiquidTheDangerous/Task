package org.example.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
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
    @Column(name="id")
    private Long id;

    @Column(name="name")
    private String name;

    @Column(name="short_name")
    private String shortName;
}
