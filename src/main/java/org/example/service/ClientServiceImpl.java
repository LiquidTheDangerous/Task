package org.example.service;

import jakarta.transaction.Transactional;
import org.example.domain.Client;
import org.example.domain.Deposit;
import org.example.domain.PlainClient;
import org.example.exceptions.ResourceNotFoundException;
import org.example.repository.ClientRepository;
import org.example.repository.DepositRepository;
import org.example.repository.PlainClientRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClientServiceImpl implements ClientService {

    private final ClientRepository clientRepository;

    private final PlainClientRepository plainClientRepository;
    private final DepositRepository depositRepository;


    public ClientServiceImpl(ClientRepository userRepository, PlainClientRepository plainClientRepository, DepositRepository depositRepository) {
        this.clientRepository = userRepository;
        this.plainClientRepository = plainClientRepository;
        this.depositRepository = depositRepository;
    }

    @Override
    public Iterable<Client> getAll() {
        return clientRepository.findAll();
    }

    @Override
    public Iterable<Client> getAll(int pageNumber, int pageSize) {
        return clientRepository.findAll(PageRequest.of(pageNumber, pageSize));
    }

    @Override
    public Client getClientById(Long id) {
        return clientRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("no such client id: " + id, "read"));
    }

    @Override
    public Client getClientByName(String name) {
        return clientRepository.getFirstByName(name).orElseThrow(() -> new ResourceNotFoundException("not such client with given name: " + name, "read"));
    }

    @Override
    public List<Deposit> getClientDepositByClientId(Long id) {
        if (!clientRepository.existsById(id)) {
            throw new ResourceNotFoundException("no such client with given id: " + id, "read");
        }
        return depositRepository.getAllByClientId(id);
    }

    @Override
    @Transactional
    public Client save(Client client) {
        client.setId(null);
        return clientRepository.save(client);
    }

    @Override
    public PlainClient save(PlainClient client) {
        client.setId(null);
        return plainClientRepository.save(client);
    }

    @Override
    @Transactional
    public void update(Client client) {
        if (!clientRepository.existsById(client.getId())) {
            throw new ResourceNotFoundException("no user to update", "update");
        }
        clientRepository.save(client);
    }

    @Override
    public void update(PlainClient plainClient) {
        if (!plainClientRepository.existsById(plainClient.getId())) {
            throw new ResourceNotFoundException("no user to update", "update");
        }
        plainClientRepository.save(plainClient);
    }

    @Override
    @Transactional
    public void deleteById(Long clientId) {
        if (!clientRepository.existsById(clientId)) {
            throw new ResourceNotFoundException("no user to delete", "delete");
        }
        clientRepository.deleteById(clientId);
    }
}
