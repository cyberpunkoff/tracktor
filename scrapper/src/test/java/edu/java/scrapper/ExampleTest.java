package edu.java.scrapper;

import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;

public class ExampleTest extends IntegrationEnvironment {
    @Test
    @SneakyThrows
    void exampleTest() {
        var connection = POSTGRES.createConnection("");
        var result = connection.prepareStatement("SELECT * from chats").executeQuery();
        System.out.println(result.getMetaData());
    }
}
