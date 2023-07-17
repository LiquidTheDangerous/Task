package org.example.controller;


import org.example.controller.body.ActionResultMessage;
import org.example.controller.body.ApiBody;
import org.example.domain.Bank;
import org.example.domain.Deposit;
import org.example.service.BankService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/bank/")
public class BankController {
    private final BankService bankService;

    public BankController(BankService bankService) {
        this.bankService = bankService;
    }

    @GetMapping("getAll")
    ResponseEntity<ApiBody<Iterable<Bank>>> getAll(@RequestParam(value = "pageNumber", defaultValue = "0", required = false) Integer pageNumber,
                                                   @RequestParam(value = "pageSize", defaultValue = "25", required = false) Integer pageSize) {
        return ResponseEntity
                .ok()
                .body(ApiBody.<Iterable<Bank>>builder()
                        .body(bankService.getAll(pageNumber, pageSize))
                        .actionResult(
                                new ActionResultMessage("read", true))
                        .build()
                );
    }

    @GetMapping("getById/{id}")
    ResponseEntity<ApiBody<Bank>> getById(@PathVariable("id") Long id) {
        return ResponseEntity.ok().body(
                ApiBody.<Bank>builder()
                        .body(bankService.getBankById(id))
                        .actionResult(new ActionResultMessage("read", true))
                        .build()
        );
    }

    @GetMapping("getByName")
    ResponseEntity<ApiBody<Bank>> getByName(@RequestParam(name = "name") String name) {
        return ResponseEntity
                .ok()
                .body(
                ApiBody.<Bank>builder()
                        .body(bankService.getBankByName(name))
                        .actionResult(new ActionResultMessage("read", true))
                        .build()
        );
    }

    @GetMapping("getByBikCode")
    ResponseEntity<ApiBody<Bank>> getByBikCode(@RequestParam(name = "bikCode") Integer bikCode) {
        return ResponseEntity
                .ok()
                .body(
                ApiBody.<Bank>builder().body(bankService.getBankByBikCode(bikCode))
                        .actionResult(new ActionResultMessage("read", true))
                        .build());
    }

    @GetMapping("getDepositByBankId/{bankId}")
    ResponseEntity<ApiBody<List<Deposit>>> getDepositByBankId(@PathVariable("bankId") Long bankId) {
        return ResponseEntity
                .ok()
                .body(
                        ApiBody.<List<Deposit>>builder()
                                .body(bankService.getBankDepositByBankId(bankId))
                                .actionResult(new ActionResultMessage("read", true))
                                .build()
                );
    }

    @DeleteMapping("deleteById/{bankId}")
    ResponseEntity<ActionResultMessage> deleteById(@PathVariable("bankId") Long bankId) {
        bankService.deleteById(bankId);
        return ResponseEntity.ok().body(new ActionResultMessage("delete", true));
    }

    @PostMapping(value = "create")
    ResponseEntity<ApiBody<Bank>> create(@RequestBody Bank bank) {
        return ResponseEntity.ok().body(
                ApiBody.<Bank>builder()
                        .body(bankService.save(bank))
                        .actionResult(new ActionResultMessage("create", true))
                        .build()
        );
    }

    @PutMapping("update")
    ResponseEntity<ActionResultMessage> update(@RequestBody Bank bank) {
        bankService.update(bank);
        return ResponseEntity.ok().body(new ActionResultMessage("update", true));
    }
}
