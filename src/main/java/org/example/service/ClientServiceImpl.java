package org.example.service;

import org.example.domain.Client;
import org.example.repository.ClientRepository;
import org.springframework.stereotype.Service;

@Service
public class ClientServiceImpl implements ClientService {

    private final ClientRepository clientRepository;

    public ClientServiceImpl(ClientRepository userRepository) {
        this.clientRepository = userRepository;
    }

    @Override
    public Iterable<Client> getAll() {
        return clientRepository.findAll();
    }
}
