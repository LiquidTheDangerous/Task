package org.example.service;

import org.example.domain.Client;
import org.example.domain.Deposit;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface ClientService {
    Iterable<Client> getAll();

    Optional<Client> getClientById(Long id);

    List<Deposit> getClientDepositByClientId(Long id);

    Client save(Client client);

    void update(Client client);

    void deleteById(Long clientId);
}
