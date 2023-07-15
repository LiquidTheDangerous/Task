package org.example.controller;

import org.example.domain.PlainDeposit;
import org.example.service.PlainDepositService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


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
    ResponseEntity<ActionResult> update(@RequestBody PlainDeposit plainDeposit) {
        plainDepositService.update(plainDeposit);
        return ResponseEntity.ok().body(new ActionResult("update", true));
    }

    @DeleteMapping("deleteById/{depositId}")
    ResponseEntity<ActionResult> deleteById(@PathVariable("depositId") Long depositId) {
        plainDepositService.deleteById(depositId);
        return ResponseEntity.ok().body(new ActionResult("delete",true));
    }
}
