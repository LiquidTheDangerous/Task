package org.example.service;

import org.example.domain.OrganizationalLegalForm;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface OrganizationalLegalFormService {
    List<OrganizationalLegalForm> getAll();
}
