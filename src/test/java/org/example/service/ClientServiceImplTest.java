package org.example.service;

import org.example.domain.Client;
import org.example.domain.Deposit;
import org.example.exceptions.ResourceNotFoundException;
import org.example.repository.ClientRepository;
import org.example.repository.DepositRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.when;

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
        var client = Client.builder().id(1L).build();
        doAnswer(invocation-> Optional.of(client)).when(clientRepository).findById(any());
        var savedClient = clientService.getClientById(1L);
        assertSame(savedClient,client);
    }

    @Test
    public void ClientServiceImpl_getClientDepositById_ReturnsAllSavedDepositsByClientId() {
        var deposits = new ArrayList<Deposit>();
        doAnswer(invocation->deposits).when(depositRepository).getAllByClientId(any());
        var savedDeposits = clientService.getClientDepositByClientId(1L);
        assertSame(savedDeposits,deposits);
    }


    @Test
    public void ClientServiceImpl_save_AssertThatServiceSaveClient() {
        var client = new Client();
        doAnswer(invocation->{
            var clientToSave = (Client)invocation.getArgument(0);
            assertSame(clientToSave,client);
            return clientToSave;
        }).when(clientRepository).save(any());
        clientService.save(client);
    }

    @Test
    public void ClientServiceImpl_update_AssertServiceThrowsResourceNotFoundException_when_NoClientToUpdate() {
        when(clientRepository.existsById(any())).thenReturn(false);
        assertThrows(ResourceNotFoundException.class,()-> clientService.update(new Client()));
    }


    @Test
    public void ClientServiceImpl_update_AssertThatServiceUpdateClient() {
        when(clientRepository.existsById(any())).thenReturn(true);
        var client = new Client();
        doAnswer(invocation->{
            var clientToUpdate = (Client)invocation.getArgument(0);
            assertSame(client,clientToUpdate);
            return clientToUpdate;
        }).when(clientRepository).save(any());
        clientService.update(client);
    }

    @Test
    public void ClientServiceImpl_deleteById_AssertThrownResourceNotFoundException_when_NoClientToDelete() {
        when(clientRepository.existsById(any())).thenReturn(false);
        assertThrows(ResourceNotFoundException.class,()-> clientService.deleteById(1L));
    }

    @Test
    public void ClientServiceImpl_deleteById_AssertThatServiceDeleteClient() {
        when(clientRepository.existsById(any())).thenReturn(true);
        Long clientId = 1L;
        doAnswer(invocation->{
            var idToDelete = invocation.getArgument(0);
            assertSame(idToDelete,clientId);
            return null;
        }).when(clientRepository).deleteById(any());
        clientService.deleteById(clientId);
    }



}
