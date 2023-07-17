package org.example.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name="client", schema = "public")
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class PlainClient {
    @Id
    @Column(name="id",nullable = false)
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    @Column(name="name", nullable = false, unique = true)
    private String name;

    @Column(name="shortname")
    private String shortname;


    @Column(name="organizational_legal_form_id")
    private Long organizationalLegalForm;
}