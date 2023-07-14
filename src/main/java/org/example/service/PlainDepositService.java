package org.example.service;

import org.example.domain.Deposit;
import org.example.domain.PlainDeposit;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public interface PlainDepositService {
    Iterable<PlainDeposit> getAll();

    Optional<PlainDeposit> getDepositById(Long id);

    void save(PlainDeposit plainDeposit);

    void update(PlainDeposit plainDeposit);

    void deleteById(Long plainDepositId);
}
