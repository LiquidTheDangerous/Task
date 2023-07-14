package org.example.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Table(name="deposit", schema = "public")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class PlainDeposit {
    @Id
    @Column(name = "id")
    private Long id;

    @Column(name = "percent")
    private Double percent;

    @Column(name = "open_date")
    private Date openDate;

    @Column(name = "time_period")
    private Integer timePeriod;

    @Column(name="bank_id")
    private Long bankId;

    @Column(name="client_id")
    private Long clientId;
}
