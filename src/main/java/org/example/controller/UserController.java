package org.example.controller;

import org.example.domain.Client;
import org.example.service.ClientService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/user/")
public class UserController {
    private final ClientService userService;
    public UserController(@Qualifier("clientServiceImpl") ClientService userService) {
        this.userService = userService;
    }
    @GetMapping("getAll")
    ResponseEntity<Iterable<Client>> getAll() {
        return ResponseEntity.ok().body(userService.getAll());
    }
}
