package org.example.service;

import jakarta.transaction.Transactional;
import org.example.domain.Client;
import org.example.domain.Deposit;
import org.example.exceptions.ResourceAlreadyExistsException;
import org.example.exceptions.ResourceNotFoundException;
import org.example.repository.ClientRepository;
import org.example.repository.DepositRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;

@Service
public class ClientServiceImpl implements ClientService {

    private final ClientRepository clientRepository;
    private final DepositRepository depositRepository;

    public ClientServiceImpl(ClientRepository userRepository, DepositRepository depositRepository) {
        this.clientRepository = userRepository;
        this.depositRepository = depositRepository;
    }

    @Override
    public Iterable<Client> getAll() {
        return clientRepository.findAll();
    }

    @Override
    public Optional<Client> getClientById(Long id) {
        return clientRepository.findById(id);
    }

    @Override
    public Set<Deposit> getClientDepositByClientId(Long id) {
        return depositRepository.getAllByClientId(id);
    }

    @Override
    @Transactional
    public void save(Client client) {
        if (clientRepository.existsById(client.getId())){
            throw new ResourceAlreadyExistsException();
        }
        clientRepository.save(client);
    }

    @Override
    @Transactional
    public void update(Client client) {
        if (!clientRepository.existsById(client.getId())){
            throw new ResourceNotFoundException();
        }
        clientRepository.save(client);
    }

    @Override
    public void deleteById(Long clientId) {
        if (!clientRepository.existsById(clientId)){
            throw new ResourceNotFoundException();
        }
        clientRepository.deleteById(clientId);
    }
}
