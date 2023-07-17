package org.example.service;

import org.example.domain.Client;
import org.example.domain.Deposit;
import org.example.domain.PlainClient;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ClientService {
    Iterable<Client> getAll();

    Iterable<Client> getAll(int pageNumber, int pageSize);

    Client getClientById(Long id);

    Client getClientByName(String name);

    List<Deposit> getClientDepositByClientId(Long id);

    Client save(Client client);

    PlainClient save (PlainClient client);

    void update(Client client);

    void update(PlainClient plainClient);

    void deleteById(Long clientId);
}
