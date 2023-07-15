package org.example.service;

import org.example.domain.Client;
import org.example.domain.Deposit;
import org.example.exceptions.ResourceAlreadyExistsException;
import org.example.repository.ClientRepository;
import org.example.repository.DepositRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doAnswer;

@ExtendWith(MockitoExtension.class)
public class ClientServiceImplTest {
    @Mock
    ClientRepository clientRepository;

    @Mock
    DepositRepository depositRepository;

    @InjectMocks
    ClientServiceImpl clientService;

    @Test
    public void ClientServiceImpl_getAll_ReturnsAllSavedClients() {
        var clients = new ArrayList<Client>();
        doAnswer(invocation->clients).when(clientRepository).findAll();
        var savedClients = clientService.getAll();
        assertSame(savedClients,clients);
    }

    @Test
    public void ClientServiceImpl_getClientById_ReturnsClientById() {
        var client = new Client();
        doAnswer(invocation->client).when(clientRepository).findById(any());
        var savedClient = clientService.getClientById(1L);
        assertSame(savedClient.get(),client);
    }

    @Test
    public void ClientServiceImpl_getClientDepositById_ReturnsAllSavedDepositsByClientId() {
        var deposits = new ArrayList<Deposit>();
        doAnswer(invocation->deposits).when(depositRepository).getAllByClientId(any());
        var savedDeposits = clientService.getClientDepositByClientId(1L);
        assertSame(savedDeposits,deposits);
    }

    @Test
    public void ClientServiceImpl_save_AssertThatResourceAlreadyExistsExceptionThrown_when_ClientAlreadyExists() {
        doAnswer(invocation->true).when(clientRepository).existsById(any());
        assertThrows(ResourceAlreadyExistsException.class,()->{
            clientService.save(new Client());
        });
    }

    @Test
    public void ClientServiceImpl_save_AssertThatServiceSaveClient() {
        var client = Client.builder().id(1L).build();
        doAnswer(invocation->false).when(clientRepository).existsById(any());
        doAnswer(invocation->{
            var clientToSave = (Client)invocation.getArgument(0);
            assertEquals(clientToSave.getId(),client.getId());
            return clientToSave;
        }).when(clientRepository).save(any());
        clientService.save(client);
    }



}
