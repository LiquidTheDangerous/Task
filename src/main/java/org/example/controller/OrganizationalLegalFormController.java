package org.example.controller;

import org.example.domain.OrganizationalLegalForm;
import org.example.service.OrganizationalLegalFormService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/organizationalLegalForm")
public class OrganizationalLegalFormController {

    private final OrganizationalLegalFormService organizationalLegalFormService;

    public OrganizationalLegalFormController(OrganizationalLegalFormService organizationalLegalFormService) {
        this.organizationalLegalFormService = organizationalLegalFormService;
    }

    @GetMapping("getAll")
    ApiBody<List<OrganizationalLegalForm>> getAll() {
        return
                ApiBody.<List<OrganizationalLegalForm>>builder()
                        .body(organizationalLegalFormService.getAll())
                        .actionResult(new ActionResult("read", true))
                        .build();
    }
}
