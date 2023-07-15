package org.example.repository;


import org.example.domain.Bank;
import org.example.domain.Client;
import org.example.domain.Deposit;
import org.example.domain.OrganizationalLegalForm;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.jdbc.Sql;


import java.util.Calendar;

import static org.junit.jupiter.api.Assertions.assertEquals;

@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
@DataJpaTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class DepositRepositoryTest {
    @Autowired
    DepositRepository depositRepository;

    @Autowired
    ClientRepository clientRepository;

    @Autowired
    BankRepository bankRepository;


    @Autowired
    TestEntityManager entityManager;

    @BeforeEach
    void createEntity() {
        var client = clientRepository.save(
                new Client(
                        1L,
                        "Semenov",
                        "Semen",
                        new OrganizationalLegalForm(
                                1L,
                                null,
                                null),
                        null)
        );
        var bank = bankRepository.save(
                new Bank(
                        1L,
                        "Open",
                        12345678,
                        null)
        );
        depositRepository.save(new Deposit(
                null,
                1.4,
                new java.sql.Date(
                        Calendar.getInstance().getTime().getTime()
                ),
                12,
                client,
                bank));
        depositRepository.save(new Deposit(
                null,
                2.0,
                new java.sql.Date(
                        Calendar.getInstance().getTime().getTime()
                ),
                6,
                client,
                bank));
                //force sync
        entityManager.flush();
        entityManager.clear();
    }

    @Test
    public void DepositRepository_getAllByClientId_ReturnsAllByClientId() {
        var clientDeposits = depositRepository.getAllByClientId(1L);
        assertEquals(2, clientDeposits.size());



        var clientDepositsFromClient = clientRepository.getFirstByName("Semenov").get().getDeposits();
        var clientDepositsFromBank = bankRepository.getFirstByName("Open").get().getDeposits();
        assertEquals(2, clientDepositsFromClient.size());
        assertEquals(2, clientDepositsFromBank.size());
    }

    @Test
    public void DepositRepository_getAllByBankId_ReturnsAllByBankId() {
        var bankDeposits = depositRepository.getAllByBankId(1L);
        assertEquals(2,bankDeposits.size());
    }

}
