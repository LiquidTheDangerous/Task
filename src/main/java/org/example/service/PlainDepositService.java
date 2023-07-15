package org.example.service;

import org.example.domain.PlainDeposit;
import org.springframework.stereotype.Service;

@Service
public interface PlainDepositService {
    Iterable<PlainDeposit> getAll();

    Iterable<PlainDeposit> getAll(int pageNumber, int pageSize);

    PlainDeposit getDepositById(Long id);

    PlainDeposit save(PlainDeposit plainDeposit);

    void update(PlainDeposit plainDeposit);

    void deleteById(Long plainDepositId);
}
