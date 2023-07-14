package org.example.repository;


import org.example.domain.Bank;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BankRepository extends JpaRepository<Bank,Long> {
    Optional<Bank> getFirstByName(String name);

    Optional<Bank> getFirstByBikCode(Long bikCode);
}
