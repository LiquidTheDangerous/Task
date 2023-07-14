package org.example.service;

import org.example.domain.Client;
import org.example.domain.Deposit;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;

@Service
public interface ClientService {
    Iterable<Client> getAll();

    Optional<Client> getClientById(Long id);

    Set<Deposit> getClientDepositByClientId(Long id);

    void save(Client client);

    void update(Client client);

    void deleteById(Long clientId);
}
