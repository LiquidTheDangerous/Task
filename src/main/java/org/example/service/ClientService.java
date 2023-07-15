package org.example.service;

import org.example.domain.Client;
import org.example.domain.Deposit;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ClientService {
    Iterable<Client> getAll();

    Iterable<Client> getAll(int pageNumber, int pageSize);

    Client getClientById(Long id);

    List<Deposit> getClientDepositByClientId(Long id);

    Client save(Client client);

    void update(Client client);

    void deleteById(Long clientId);
}
