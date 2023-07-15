package org.example.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@Entity
@Table(name = "deposit", schema = "public")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Deposit {
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;

    @Column(name="percent", nullable = false)
    private Double percent;

    @Column(name="open_date", nullable = false)
    private Date openDate;

    @Column(name="time_period", nullable = false)
    private Integer timePeriod;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "client_id",nullable = false)
    Client client;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "bank_id",nullable = false)
    Bank bank;
}
