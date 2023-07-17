package org.example.repository;

import org.example.domain.PlainClient;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlainClientRepository extends JpaRepository<PlainClient,Long> {
}
