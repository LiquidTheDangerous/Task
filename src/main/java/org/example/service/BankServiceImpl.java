package org.example.service;

import jakarta.transaction.Transactional;
import org.example.domain.Bank;
import org.example.domain.Deposit;
import org.example.exceptions.ResourceNotFoundException;
import org.example.repository.BankRepository;
import org.example.repository.DepositRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

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
    public Iterable<Bank> getAll(int pageNumber, int pageSize) {
        return bankRepository.findAll(PageRequest.of(pageNumber, pageSize));
    }

    @Override
    public Bank getBankById(Long id) {
        return bankRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("no such bank id: " + id, "read"));
    }

    @Override
    public Bank getBankByName(String bankName) {
        return bankRepository.getFirstByName(bankName).orElseThrow(()->new ResourceNotFoundException("no such bank with given name: " + bankName,"read"));
    }

    @Override
    public Bank getBankByBikCode(Integer bikCode) {
        return bankRepository.getFirstByBikCode(bikCode).orElseThrow(()->new ResourceNotFoundException("no such bank with given bik code: "+ bikCode,"read"));
    }

    @Override
    public List<Deposit> getBankDepositByBankId(Long bankId) {
        if (!depositRepository.existsById(bankId)) {
            throw new ResourceNotFoundException("no such bank id: "+ bankId,"read");
        }
        return depositRepository.getAllByBankId(bankId);
    }

    @Override
    @Transactional
    public Bank save(Bank bank) {
        bank.setId(null);
        return bankRepository.save(bank);
    }

    @Override
    @Transactional
    public void update(Bank bank) {
        if (!bankRepository.existsById(bank.getId())) {
            throw new ResourceNotFoundException("no bank to update", "update");
        }
        bankRepository.save(bank);
    }

    @Override
    @Transactional
    public void deleteById(Long bankId) {
        if (!bankRepository.existsById(bankId)) {
            throw new ResourceNotFoundException("no bank to delete", "delete");
        }
        bankRepository.deleteById(bankId);
    }
}
