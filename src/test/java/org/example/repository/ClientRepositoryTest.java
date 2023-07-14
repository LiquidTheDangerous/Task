package org.example.repository;

import org.example.domain.Client;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import junit.framework.Assert;

@DataJpaTest()
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class ClientRepositoryTest {
    @Autowired
    private ClientRepository clientRepository;

    @Test
    public void ClientRepository_getFirstByName_ReturnsFirstByName() {
        var clientFirst = new Client(1L,"Client1", null,null,null);
        var clientSecond = new Client(2L,"Client2", null,null,null);

        clientRepository.save(clientFirst);
        clientRepository.save(clientSecond);

        var result = clientRepository.getFirstByName("Client1");
        Assert.assertTrue(result.isPresent());
        Assert.assertEquals("Client1", result.get().getName());
    }
}
