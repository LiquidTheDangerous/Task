package org.example.controller;

import org.example.domain.PlainDeposit;
import org.example.exceptions.ResourceNotFoundException;
import org.example.service.PlainDepositService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.Map;

@RestController
@RequestMapping("api/deposit")
public class DepositController {

    private final PlainDepositService plainDepositService;

    public DepositController(PlainDepositService plainDepositService) {
        this.plainDepositService = plainDepositService;
    }

    @GetMapping("getAll")
    ResponseEntity<Iterable<PlainDeposit>> getAll() {
        return ResponseEntity.
                ok()
                .body(plainDepositService.getAll());
    }

    @PostMapping("create")
    ResponseEntity<PlainDeposit> create(@RequestBody PlainDeposit plainDeposit) {
        return ResponseEntity.ok().body(plainDepositService.save(plainDeposit));
    }

    @PutMapping("update")
    ResponseEntity<Map<String,Boolean>> update(@RequestBody PlainDeposit plainDeposit) {
        try {
            plainDepositService.update(plainDeposit);
        } catch (Exception exception) {
            return ResponseEntity
                    .badRequest()
                    .body(Collections.singletonMap("update", Boolean.FALSE));
        }
        return ResponseEntity
                .ok()
                .body(Collections.singletonMap("update", Boolean.TRUE));
    }

    @DeleteMapping("deleteById/{depositId}")
    ResponseEntity<Map<String,Boolean>> deleteById(@PathVariable("depositId") Long depositId) {
        try {
            plainDepositService.deleteById(depositId);
        } catch (Exception exception) {
            return ResponseEntity
                    .badRequest()
                    .body(Collections.singletonMap("delete",Boolean.FALSE));
        }
        return ResponseEntity
                .ok()
                .body(Collections.singletonMap("delete",Boolean.TRUE));
    }
}
