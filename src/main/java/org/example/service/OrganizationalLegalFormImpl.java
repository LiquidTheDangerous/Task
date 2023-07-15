package org.example.service;

import org.example.domain.OrganizationalLegalForm;
import org.example.repository.OrganizationalLegalFormRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrganizationalLegalFormImpl implements OrganizationalLegalFormService {

    private final OrganizationalLegalFormRepository organizationalLegalFormRepository;

    public OrganizationalLegalFormImpl(OrganizationalLegalFormRepository organizationalLegalFormLongJpaRepository) {
        this.organizationalLegalFormRepository = organizationalLegalFormLongJpaRepository;
    }

    @Override
    public List<OrganizationalLegalForm> getAll() {
        return organizationalLegalFormRepository.findAll();
    }
}
