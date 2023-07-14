package org.example.service;

import jakarta.transaction.Transactional;
import org.example.domain.PlainDeposit;
import org.example.exceptions.ResourceAlreadyExistsException;
import org.example.exceptions.ResourceNotFoundException;
import org.example.repository.PlainDepositRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PlainDepositServiceImpl implements PlainDepositService {

    private final PlainDepositRepository plainDepositRepository;

    public PlainDepositServiceImpl(PlainDepositRepository plainDepositRepository) {
        this.plainDepositRepository = plainDepositRepository;
    }

    @Override
    public Iterable<PlainDeposit> getAll() {
        return plainDepositRepository.findAll();
    }

    @Override
    public Optional<PlainDeposit> getDepositById(Long id) {
        return plainDepositRepository.findById(id);
    }

    @Override
    @Transactional
    public void save(PlainDeposit plainDeposit) {
        if (plainDepositRepository.existsById(plainDeposit.getId())) {
            throw new ResourceAlreadyExistsException();
        }
        plainDepositRepository.save(plainDeposit);
    }


    @Override
    @Transactional
    public void update(PlainDeposit plainDeposit) {
        if (!plainDepositRepository.existsById(plainDeposit.getId())) {
            throw new ResourceNotFoundException();
        }
        plainDepositRepository.save(plainDeposit);

    }

    @Override
    @Transactional
    public void deleteById(Long depositId) {
        if (!plainDepositRepository.existsById(depositId)) {
            throw new ResourceNotFoundException();
        }
        plainDepositRepository.deleteById(depositId);
    }
}