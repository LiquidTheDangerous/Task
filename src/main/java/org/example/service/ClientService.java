package org.example.service;

import org.example.domain.Client;
import org.springframework.stereotype.Service;

@Service
public interface ClientService {
    Iterable<Client> getAll();
}
