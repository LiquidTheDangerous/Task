package org.example.controller;

import org.example.domain.Client;
import org.example.domain.Deposit;
import org.example.domain.PlainClient;
import org.example.service.ClientService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/client/")
public class ClientController {
    private final ClientService clientService;

    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }

    @GetMapping("getAll")
    ResponseEntity<ApiBody<Iterable<Client>>> getAll(@RequestParam(name = "pageNumber", defaultValue = "0", required = false) Integer pageNumber,
                                                     @RequestParam(name = "pageSize", defaultValue = "25", required = false) Integer pageSize) {
        var clients = clientService.getAll(pageNumber,pageSize);
        return ResponseEntity
                .ok()
                .body(
                        ApiBody.<Iterable<Client>>builder()
                                .body(clients)
                                .actionResult(new ActionResultMessage("read", true))
                                .build()
                );
    }

    @GetMapping("getById/{id}")
    ResponseEntity<ApiBody<Client>> getById(@PathVariable(name = "id") Long id) {
        return ResponseEntity
                .ok()
                .body(
                        ApiBody.<Client>builder()
                                .body(clientService.getClientById(id))
                                .actionResult(new ActionResultMessage("read", true))
                                .build()
                );
    }

    @GetMapping("getByName")
    ResponseEntity<ApiBody<Client>> getByName(@RequestParam(name = "name") String name) {
        return ResponseEntity.ok().body(
                ApiBody.<Client>builder()
                        .body(clientService.getClientByName(name))
                        .actionResult(new ActionResultMessage("read", true))
                        .build()
        );
    }

    @DeleteMapping("deleteById/{clientId}")
    ResponseEntity<ActionResultMessage> deleteById(@PathVariable("clientId") Long clientId) {
        clientService.deleteById(clientId);
        return ResponseEntity
                .ok()
                .body(new ActionResultMessage("delete", true));
    }

    @PostMapping("create")
    ResponseEntity<ApiBody<PlainClient>> create(@RequestBody PlainClient client) {
        return ResponseEntity.ok().body(
                ApiBody.<PlainClient>builder()
                        .body(clientService.save(client))
                        .actionResult(new ActionResultMessage("create", true))
                        .build()
        );
    }

    @PutMapping("update")
    ResponseEntity<ActionResultMessage> update(@RequestBody PlainClient client) {
        clientService.update(client);
        return ResponseEntity
                .ok()
                .body(new ActionResultMessage("update", true));
    }

    @GetMapping("getDepositByClientId/{clientId}")
    ResponseEntity<ApiBody<List<Deposit>>> getDepositByClientId(@PathVariable("clientId") Long clientId) {
        return ResponseEntity
                .ok()
                .body(
                        ApiBody.<List<Deposit>>builder()
                                .body(clientService.getClientDepositByClientId(clientId))
                                .actionResult(new ActionResultMessage("read", true))
                                .build()
                );
    }
}
