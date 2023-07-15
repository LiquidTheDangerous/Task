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
    ResponseEntity<ApiBody<Iterable<Client>>> getAll(@RequestParam(name = "pageNumber", defaultValue = "0", required = false) Integer pageNumber,
                                                     @RequestParam(name = "pageSize", defaultValue = "25", required = false) Integer pageSize) {
        return ResponseEntity
                .ok()
                .body(
                        ApiBody.<Iterable<Client>>builder()
                                .body(clientService.getAll(pageNumber, pageSize))
                                .actionResult(new ActionResult("read", true))
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
                                .actionResult(new ActionResult("read", true))
                                .build()
                );
    }

    @GetMapping("getByName")
    ResponseEntity<ApiBody<Client>> getByName(@RequestParam(name = "name") String name) {
        return ResponseEntity.ok().body(
                ApiBody.<Client>builder()
                        .body(clientService.getClientByName(name))
                        .actionResult(new ActionResult("read", true))
                        .build()
        );
    }

    @DeleteMapping("deleteById/{clientId}")
    ResponseEntity<ActionResult> deleteById(@PathVariable("clientId") Long clientId) {
        clientService.deleteById(clientId);
        return ResponseEntity
                .ok()
                .body(new ActionResult("delete", true));
    }

    @PostMapping("create")
    ResponseEntity<ApiBody<Client>> create(@RequestBody Client client) {
        return ResponseEntity.ok().body(
                ApiBody.<Client>builder()
                        .body(clientService.save(client))
                        .actionResult(new ActionResult("create", true))
                        .build()
        );
    }

    @PutMapping("update")
    ResponseEntity<ActionResult> update(@RequestBody Client client) {
        clientService.update(client);
        return ResponseEntity
                .ok()
                .body(new ActionResult("update", true));
    }

    @GetMapping("getDepositByClientId/{clientId}")
    ResponseEntity<ApiBody<List<Deposit>>> getDepositByClientId(@PathVariable("clientId") Long clientId) {
        return ResponseEntity
                .ok()
                .body(
                        ApiBody.<List<Deposit>>builder()
                                .body(clientService.getClientDepositByClientId(clientId))
                                .actionResult(new ActionResult("read", true))
                                .build()
                );
    }
}
