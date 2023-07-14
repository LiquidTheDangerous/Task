package org.example.service;

import jakarta.transaction.Transactional;
import org.example.domain.Bank;
import org.example.domain.Deposit;
import org.example.exceptions.ResourceAlreadyExistsException;
import org.example.exceptions.ResourceNotFoundException;
import org.example.repository.BankRepository;
import org.example.repository.DepositRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;

@Service
public class BankServiceImpl implements BankService {

    private final BankRepository bankRepository;
    private final DepositRepository depositRepository;

    public BankServiceImpl(BankRepository bankRepository, DepositRepository depositRepository) {
        this.bankRepository = bankRepository;
        this.depositRepository = depositRepository;
    }

    @Override
    public Iterable<Bank> getAll() {
        return bankRepository.findAll();
    }

    @Override
    public Optional<Bank> getBankById(Long id) {
        return bankRepository.findById(id);
    }

    @Override
    public Set<Deposit> getBankDepositByBankId(Long bankId) {
        return depositRepository.getAllByBankId(bankId);
    }

    @Override
    @Transactional
    public void save(Bank bank) {
        if (bankRepository.existsById(bank.getId())) {
            throw new ResourceAlreadyExistsException();
        }
        bankRepository.save(bank);
    }

    @Override
    @Transactional
    public void update(Bank bank) {
        if (!bankRepository.existsById(bank.getId())){
            throw new ResourceNotFoundException();
        }
        bankRepository.save(bank);
    }

    @Override
    @Transactional
    public void deleteById(Long bankId) {
        if (!bankRepository.existsById(bankId)){
            throw new ResourceNotFoundException();
        }
        bankRepository.deleteById(bankId);
    }
}
