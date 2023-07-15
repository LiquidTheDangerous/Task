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
    ResponseEntity<ApiBody<Iterable<PlainDeposit>>> getAll(@RequestParam(name = "pageNumber", defaultValue = "0", required = false) Integer pageNumber,
                                                           @RequestParam(name = "pageSize", defaultValue = "25", required = false) Integer pageSize) {
        return ResponseEntity.
                ok()
                .body(
                        ApiBody.<Iterable<PlainDeposit>>builder()
                                .body(plainDepositService.getAll(pageNumber, pageSize))
                                .actionResult(new ActionResultMessage("read", true))
                                .build()
                );
    }

    @GetMapping("getById/{id}")
    ResponseEntity<ApiBody<PlainDeposit>> getById(@PathVariable("id") Long id) {
        return ResponseEntity
                .ok()
                .body(
                        ApiBody.<PlainDeposit>builder()
                                .body(plainDepositService.getDepositById(id))
                                .actionResult(new ActionResultMessage("read", true))
                                .build()
                );
    }

    @PostMapping("create")
    ResponseEntity<ApiBody<PlainDeposit>> create(@RequestBody PlainDeposit plainDeposit) {
        return ResponseEntity
                .ok()
                .body(
                        ApiBody.<PlainDeposit>builder()
                                .body(plainDepositService.save(plainDeposit))
                                .actionResult(new ActionResultMessage("create", true))
                                .build()
                );
    }

    @PutMapping("update")
    ResponseEntity<ActionResultMessage> update(@RequestBody PlainDeposit plainDeposit) {
        plainDepositService.update(plainDeposit);
        return ResponseEntity
                .ok()
                .body(new ActionResultMessage("update", true));
    }

    @DeleteMapping("deleteById/{depositId}")
    ResponseEntity<ActionResultMessage> deleteById(@PathVariable("depositId") Long depositId) {
        plainDepositService.deleteById(depositId);
        return ResponseEntity
                .ok()
                .body(new ActionResultMessage("delete", true));
    }
}
