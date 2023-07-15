package org.example.controller;


import org.example.domain.Bank;
import org.example.domain.Deposit;
import org.example.service.BankService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("api/bank/")
public class BankController {
    private final BankService bankService;

    public BankController(BankService bankService) {
        this.bankService = bankService;
    }

    @GetMapping("getAll")
    ResponseEntity<Iterable<Bank>> getAll() {
        return ResponseEntity
                .ok()
                .body(bankService.getAll());

    }

    @GetMapping("getDepositByBankId/{bankId}")
    ResponseEntity<List<Deposit>> getDepositByBankId(@PathVariable("bankId") Long bankId) {
        return ResponseEntity
                .ok()
                .body(bankService.getBankDepositByBankId(bankId));
    }

    @DeleteMapping("deleteById/{bankId}")
    ResponseEntity<Map<String,Boolean>> deleteById(@PathVariable("bankId") Long bankId) {
        try {
            bankService.deleteById(bankId);
        } catch (Exception exception) {
            return ResponseEntity
                    .badRequest()
                    .body(Collections.singletonMap("delete",Boolean.FALSE));
        }
        return ResponseEntity
                .ok()
                .body(Collections.singletonMap("delete", Boolean.TRUE));
    }

    @PostMapping("create")
    ResponseEntity<Bank> create(@RequestBody Bank bank) {
        return ResponseEntity.ok().body(bankService.save(bank));
    }

    @PutMapping("update")
    ResponseEntity<Map<String,Boolean>> update(@RequestBody Bank bank) {
        try {
            bankService.update(bank);
        } catch (Exception exception) {
            return ResponseEntity
                    .badRequest()
                    .body(Collections.singletonMap("update",Boolean.FALSE));
        }
        return ResponseEntity
                .ok()
                .body(Collections.singletonMap("update",Boolean.TRUE));
    }
}
