package org.example.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.domain.Client;
import org.example.domain.Deposit;
import org.example.exceptions.ResourceNotFoundException;
import org.example.service.ClientServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.Calendar;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = ClientController.class)
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
public class ClientControllerTest {

    @MockBean
    ClientServiceImpl clientService;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    MockMvc mockMvc;

    @Test
    public void ClientController_create_ReturnCreated() throws Exception {
        var client = Client.builder()
                .name("client")
                .shortname("client")
                .id(null)
                .build();
        when(clientService.save(any(Client.class))).thenReturn(client);
        var response = mockMvc.perform(post("/api/client/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(client)));

        response.andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers
                        .content()
                        .json(objectMapper.writeValueAsString(
                                ApiBody.<Client>builder()
                                        .body(client)
                                        .actionResult(new ActionResultMessage("create", true))
                                        .build())));
    }

    @Test
    public void ClientController_update_AssertThatControllerUpdateClient() throws Exception {
        var client = Client.builder().id(1L).name("name").shortname("name").build();
        doAnswer(invocation -> {
            var clientToUpdate = invocation.getArgument(0);
            assertEquals(clientToUpdate, client);
            return null;
        }).when(clientService).update(any(Client.class));

        var response = mockMvc.perform(put("/api/client/update")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(client)));
        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content()
                        .json(objectMapper.writeValueAsString(
                                new ActionResultMessage("update", true))));
    }

    @Test
    public void ClientController_update_AssertThatControllerReturnsNotFoundStatus_when_NoSuchClientToUpdate() throws Exception {
        var client = Client.builder().id(1L).name("name").shortname("name").build();
        var msg = "no such client to update";
        var op = "update";
        doAnswer(invocation -> {
            throw new ResourceNotFoundException(msg, op);
        }).when(clientService).update(any(Client.class));

        var response = mockMvc.perform(put("/api/client/update")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(client)));

        response.andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(MockMvcResultMatchers.content()
                        .json(objectMapper.writeValueAsString(
                                new ErrorBody(new ActionResultMessage(op, false), msg))));
    }

    @Test
    public void ClientController_deleteById_AssertThatControllerReturnsStatusNotFound_when_NoClientToUpdate() throws Exception {
        var clientId = 1L;
        var msg = "no such client to delete";
        var op = "delete";
        doAnswer(invocation -> {
            throw new ResourceNotFoundException(msg, op);
        }).when(clientService).deleteById(any(Long.class));

        var response = mockMvc.perform(delete("/api/client/deleteById/{id}", clientId));

        response.andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(MockMvcResultMatchers.content().json(
                        objectMapper.writeValueAsString(objectMapper.writeValueAsString(
                                new ErrorBody(new ActionResultMessage(op, false), msg)))));
    }

    @Test
    public void ClientController_deleteById_AssertThatControllerDeleteById() throws Exception {
        var clientId = 1L;
        var op = "delete";
        doAnswer(invocation -> {
            var clientIdToDelete = invocation.getArgument(0);
            assertEquals(clientIdToDelete, clientId);
            return null;
        }).when(clientService).deleteById(any(Long.class));

        var response = mockMvc.perform(delete("/api/client/deleteById/{id}", clientId));

        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(
                        objectMapper.writeValueAsString(new ActionResultMessage(op, true))
                ));
    }

    @Test
    public void ClientController_getAll_ReturnsAllSavedClients() throws Exception {
        var clients = new ArrayList<Client>();
        clients.add(new Client(1L, "name1", "shortname1", null, null));
        clients.add(new Client(1L, "name2", "shortname2", null, null));
        when(clientService.getAll(any(Integer.class), any(Integer.class)))
                .thenReturn(clients);
        var response = mockMvc.perform(get("/api/client/getAll")
                .param("pageNumber", Integer.toString(1))
                .param("pageSize", Integer.toString(25)));

        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(
                        objectMapper.writeValueAsString(
                                ApiBody.<ArrayList<Client>>builder()
                                        .body(clients)
                                        .actionResult(new ActionResultMessage("read", true))
                                        .build())));
    }

    @Test
    public void ClientController_getById_ReturnsClientById() throws Exception {
        var client = Client.builder().id(1L).name("name").shortname("shortname").build();
        when(clientService.getClientById(any(Long.class))).thenReturn(client);

        var response = mockMvc.perform(get("/api/client/getById/{id}", client.getId()));

        response.andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(
                        new ApiBody<>(
                                new ActionResultMessage("read", true),
                                client))));
    }

    @Test
    public void ClientController_getById_ReturnsStatusNotFound_when_ClientByIdNotFound() throws Exception {
        var clientId = 1L;
        var op = "read";
        var msg = "client not found";
        doAnswer(invocation -> {
            throw new ResourceNotFoundException(msg, op);
        }).when(clientService).getClientById(any(Long.class));

        var response = mockMvc.perform(get("/api/client/getById/{id}", clientId));

        response.andExpect(status().isNotFound())
                .andExpect(content().json(objectMapper.writeValueAsString(
                        new ErrorBody(new ActionResultMessage(op, false), msg)
                )));
    }

    @Test
    public void ClientController_getByName_ReturnsClientByName() throws Exception {
        var client = Client.builder().id(1L).name("name").shortname("shortname").build();
        when(clientService.getClientByName(any(String.class))).thenReturn(client);

        var response = mockMvc.perform(get("/api/client/getByName")
                .param("name", client.getName()));

        response.andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(
                        new ApiBody<>(
                                new ActionResultMessage("read", true),
                                client))));
    }

    @Test
    public void ClientController_getByName_ReturnsStatusNotFound_when_ClientNotFound() throws Exception {
        var client = Client.builder().id(1L).name("name").shortname("shortname").build();
        var op = "read";
        var msg = "client not found";
        doAnswer(invocation -> {
            throw new ResourceNotFoundException(msg, op);
        }).when(clientService).getClientByName(any(String.class));

        var response = mockMvc.perform(get("/api/client/getByName")
                .param("name", client.getName()));

        response.andExpect(status().isNotFound())
                .andExpect(content().json(objectMapper.writeValueAsString(
                        new ErrorBody(new ActionResultMessage(op, false), msg)
                )));
    }

    @Test
    public void ClientController_getDepositsByClientId_ReturnsClientDeposits() throws Exception {
        var clientId = 1L;
        var deposits = new ArrayList<Deposit>();
        deposits.add(new Deposit(null, 1.3, new java.sql.Date(Calendar.getInstance().getTime().getTime()), 12, null, null));
        deposits.add(new Deposit(null, 2.8, new java.sql.Date(Calendar.getInstance().getTime().getTime()), 24, null, null));

        when(clientService.getClientDepositByClientId(any(Long.class))).thenReturn(deposits);

        var response = mockMvc.perform(get("/api/client/getDepositByClientId/{id}", clientId));

        response.andExpect(status().isOk())
                .andExpect(content().json(
                        objectMapper.writeValueAsString(
                                new ApiBody<>(
                                        new ActionResultMessage("read", true),
                                        deposits))));
    }
    @Test
    public void ClientController_getDepositsByClientId_ReturnsStatusNotFound_when_NoSuchClientId() throws Exception {
        var clientId = 1L;
        var op = "read";
        var msg = "client not found";
        doAnswer(invocation -> {
            throw new ResourceNotFoundException(msg, op);
        }).when(clientService).getClientDepositByClientId(any(Long.class));

        var response = mockMvc.perform(get("/api/client/getDepositByClientId/{id}",clientId));

        response.andExpect(status().isNotFound())
                .andExpect(content().json(objectMapper.writeValueAsString(
                        new ErrorBody(new ActionResultMessage(op, false), msg)
                )));
    }


}
