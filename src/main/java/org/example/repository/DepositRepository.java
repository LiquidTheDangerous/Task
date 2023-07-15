package org.example.repository;

import org.example.domain.Deposit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Set;


public interface DepositRepository extends JpaRepository<Deposit,Long> {


    @Query("SELECT d FROM Deposit d where d.client.id=:clientId")
    List<Deposit> getAllByClientId(@Param("clientId") Long clientId);

    @Query("SELECT d FROM Deposit d where d.bank.id=:bankId")
    List<Deposit> getAllByBankId(@Param("bankId") Long bankId);


}
