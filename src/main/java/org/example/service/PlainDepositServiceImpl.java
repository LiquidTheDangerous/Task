package org.example.service;

import jakarta.transaction.Transactional;
import org.example.domain.PlainDeposit;
import org.example.exceptions.ResourceNotFoundException;
import org.example.repository.PlainDepositRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

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
    public Iterable<PlainDeposit> getAll(int pageNumber, int pageSize) {
        return plainDepositRepository.findAll(PageRequest.of(pageNumber,pageSize));
    }

    @Override
    public PlainDeposit getDepositById(Long id) {
        return plainDepositRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("no such deposit id: " + id, "read"));
    }

    @Override
    @Transactional
    public PlainDeposit save(PlainDeposit plainDeposit) {
        plainDeposit.setId(null);
        return plainDepositRepository.save(plainDeposit);
    }


    @Override
    @Transactional
    public void update(PlainDeposit plainDeposit) {
        if (!plainDepositRepository.existsById(plainDeposit.getId())) {
            throw new ResourceNotFoundException("no deposit to update", "update");
        }
        plainDepositRepository.save(plainDeposit);
    }

    @Override
    @Transactional
    public void deleteById(Long depositId) {
        if (!plainDepositRepository.existsById(depositId)) {
            throw new ResourceNotFoundException("no deposit to delete", "delete");
        }
        plainDepositRepository.deleteById(depositId);
    }
}
