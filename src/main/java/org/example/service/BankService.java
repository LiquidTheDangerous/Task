package org.example.service;

import org.example.domain.Bank;
import org.example.domain.Deposit;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;

@Service
public interface BankService {
    Iterable<Bank> getAll();

    Optional<Bank> getBankById(Long id);

    Set<Deposit> getBankDepositByBankId(Long bankId);

    void save(Bank bank);

    void update(Bank bank);

    void deleteById(Long bankId);
}
