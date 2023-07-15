package org.example.repository;

import junit.framework.Assert;
import org.assertj.core.api.Assertions;
import org.example.domain.Bank;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;

@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class BankRepositoryTest {
    @Autowired
    private BankRepository bankRepository;

    @Test
    public void BankRepository_getFirstByName_ReturnsFirstByName() {
        var bankFist
                = new Bank(null, "Bank1", 123456789L, null);
        var bankSecond
                = new Bank(null, "Bank2", 123456789L, null);
        bankRepository.save(bankFist);
        bankRepository.save(bankSecond);
        var result = bankRepository.getFirstByName("Bank1");
        Assert.assertTrue(result.isPresent());
        Assert.assertEquals(1L, (long) result.get().getId());

    }

    @Test
    public void BankRepository_getFirstByBikCode_ReturnsFirstByBikCode() {
        var bankFist
                = new Bank(1L, "Bank1", 123456788L, null);
        var bankSecond
                = new Bank(2L, "Bank2", 123456789L, null);
                bankRepository.save(bankFist);
        bankRepository.save(bankFist);
        bankRepository.save(bankSecond);
        var result = bankRepository.getFirstByBikCode(123456788L);
        Assert.assertTrue(result.isPresent());
        Assert.assertEquals(123456788L, (long) result.get().getBikCode());
    }
}
