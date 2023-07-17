package org.example.repository;



import org.example.domain.Bank;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.DirtiesContext;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class BankRepositoryTest {
    @Autowired
    private BankRepository bankRepository;

    @Test
    public void BankRepository_getFirstByName_ReturnsFirstByName() {
        var bankFist
                = new Bank(null, "Bank1", 123456788);
        var bankSecond
                = new Bank(null, "Bank2", 123456789);
        bankRepository.save(bankFist);
        bankRepository.save(bankSecond);
        var result = bankRepository.getFirstByName("Bank1");
        assertTrue(result.isPresent());
        assertEquals(1L, (long) result.get().getId());

    }

    @Test
    public void BankRepository_getFirstByBikCode_ReturnsFirstByBikCode() {
        var bankFist
                = new Bank(1L, "Bank1", 123456788);
        var bankSecond
                = new Bank(2L, "Bank2", 123456789);
                bankRepository.save(bankFist);
        bankRepository.save(bankFist);
        bankRepository.save(bankSecond);
        var result = bankRepository.getFirstByBikCode(123456788);
        assertTrue(result.isPresent());
        assertEquals(123456788L, (long) result.get().getBikCode());
    }
}
