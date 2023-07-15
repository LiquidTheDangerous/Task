package org.example.controller;

import org.example.domain.Client;
import org.example.domain.Deposit;
import org.example.service.ClientService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;

@RestController
@RequestMapping("api/user/")
public class ClientController {
    private final ClientService clientService;

    public ClientController(@Qualifier("clientServiceImpl") ClientService userService) {
        this.clientService = userService;
    }

    @GetMapping("getAll")
    ResponseEntity<Iterable<Client>> getAll() {
        return ResponseEntity.ok().body(clientService.getAll());
    }


    @DeleteMapping("deleteById/{clientId}")
    ResponseEntity<Map<String, Boolean>> deleteById(@PathVariable("clientId") Long clientId) {
        try {
            clientService.deleteById(clientId);
        } catch (Exception exception) {
            return ResponseEntity
                    .badRequest()
                    .body(Collections.singletonMap("delete", Boolean.FALSE));
        }
        return ResponseEntity
                .ok()
                .body(Collections.singletonMap("delete", Boolean.TRUE));
    }

    @PostMapping("create")
    ResponseEntity<Map<String, Boolean>> create(@RequestBody Client client) {
        try {
            clientService.save(client);
        } catch (Exception exception) {
            return ResponseEntity
                    .badRequest()
                    .body(Collections.singletonMap("create", Boolean.FALSE));
        }
        return ResponseEntity
                .ok()
                .body(Collections.singletonMap("create", Boolean.TRUE));
    }

    @PutMapping("update")
    ResponseEntity<Map<String,Boolean>> update(@RequestBody Client client) {
        try {
            clientService.update(client);
        } catch (Exception exception) {
            return  ResponseEntity
                    .badRequest()
                    .body(Collections.singletonMap("update",Boolean.FALSE));
        }
        return ResponseEntity
                .ok()
                .body(Collections.singletonMap("update",Boolean.TRUE));
    }

    @GetMapping("getDepositByClientId/{clientId}")
    ResponseEntity<List<Deposit>> getDepositByClientId(@PathVariable("clientId") Long clientId) {
        return ResponseEntity
                .ok()
                .body(clientService.getClientDepositByClientId(clientId));
    }
}
