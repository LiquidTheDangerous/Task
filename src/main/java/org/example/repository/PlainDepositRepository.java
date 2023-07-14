package org.example.repository;

import org.example.domain.PlainDeposit;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlainDepositRepository extends JpaRepository<PlainDeposit, Long> {
}
