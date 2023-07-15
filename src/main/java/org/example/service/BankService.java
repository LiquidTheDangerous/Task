package org.example.service;

import org.example.domain.Bank;
import org.example.domain.Deposit;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface BankService {
    Iterable<Bank> getAll();

    Optional<Bank> getBankById(Long id);

    List<Deposit> getBankDepositByBankId(Long bankId);

    Bank save(Bank bank);

    void update(Bank bank);

    void deleteById(Long bankId);
}
