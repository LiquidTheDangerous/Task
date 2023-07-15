package org.example.controller;

import org.example.domain.Client;
import org.example.domain.Deposit;
import org.example.service.ClientService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/user/")
public class ClientController {
    private final ClientService clientService;

    public ClientController(@Qualifier("clientServiceImpl") ClientService userService) {
        this.clientService = userService;
    }

    @GetMapping("getAll")
    ResponseEntity<Iterable<Client>> getAll(@RequestParam(name = "pageNumber", defaultValue = "0", required = false) Integer pageNumber,
                                            @RequestParam(name = "pageSize", defaultValue = "25", required = false) Integer pageSize) {
        return ResponseEntity.ok().body(clientService.getAll(pageNumber,pageSize));
    }

    @GetMapping("getById/{id}")
    ResponseEntity<Client> getById(@PathVariable(name = "id") Long id) {
        return ResponseEntity.ok().body(clientService.getClientById(id));
    }

    @DeleteMapping("deleteById/{clientId}")
    ResponseEntity<ActionResult> deleteById(@PathVariable("clientId") Long clientId) {
        clientService.deleteById(clientId);
        return ResponseEntity.ok().body(new ActionResult("delete", true));
    }

    @PostMapping("create")
    ResponseEntity<Client> create(@RequestBody Client client) {
        return ResponseEntity.ok().body(clientService.save(client));
    }

    @PutMapping("update")
    ResponseEntity<ActionResult> update(@RequestBody Client client) {
        clientService.update(client);
        return ResponseEntity.ok().body(new ActionResult("update", true));
    }

    @GetMapping("getDepositByClientId/{clientId}")
    ResponseEntity<List<Deposit>> getDepositByClientId(@PathVariable("clientId") Long clientId) {
        return ResponseEntity.ok().body(clientService.getClientDepositByClientId(clientId));
    }
}
