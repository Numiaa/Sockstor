package me.numiaa.sockstortest;

import me.numiaa.sockstor.SockstorApplication;
import me.numiaa.sockstor.domain.Sockstor;
import me.numiaa.sockstor.repo.SockstorRepository;
import me.numiaa.sockstortest.utils.TestUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;

import java.util.Locale;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(classes = SockstorApplication.class, webEnvironment=SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource("/application-test.properties")
@Sql(value = {"/query/add-stor-before.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(value = {"/query/add-stor-after.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
public class PostOutcomeTest {

    @LocalServerPort
    int randomServerPort;

    @Autowired
    SockstorRepository sockstorRepository;

    @Autowired
    TestRestTemplate restTemplate;

    @Test
    void withdrawUniqueTypeSocks() throws Exception {
        String color = "green";
        byte cottonPart = 50;
        long quantity = 10;

        ResponseEntity<Object> result = TestUtils.postOutcome(color, cottonPart, quantity, restTemplate, randomServerPort);
        assertThat(result.getStatusCodeValue()).isEqualTo(400);
    }

    @Test
    void withdrawExistingTypeSocks() throws Exception {
        String color = "red";
        byte cottonPart = 50;
        long quantity = 1;

        ResponseEntity<Object> result = TestUtils.postOutcome(color, cottonPart, quantity, restTemplate, randomServerPort);
        assertThat(result.getStatusCodeValue()).isEqualTo(200);

        Sockstor sockstor = sockstorRepository.findByColorAndCottonPart(color, cottonPart);
        assertThat(sockstor.getQuantity()).isEqualTo(10);
    }

    @Test
    void withdrawNegativeQuantity() throws Exception {
        String color = "red";
        byte cottonPart = 50;
        long quantity = -10;

        ResponseEntity<Object> result = TestUtils.postOutcome(color, cottonPart, quantity, restTemplate, randomServerPort);
        assertThat(result.getStatusCodeValue()).isEqualTo(400);
    }

    @Test
    void withdrawUpperCaseColorName() throws Exception {
        String color = "RED";
        byte cottonPart = 50;
        long quantity = 1;

        ResponseEntity<Object> result = TestUtils.postOutcome(color, cottonPart, quantity, restTemplate, randomServerPort);
        assertThat(result.getStatusCodeValue()).isEqualTo(200);

        Sockstor sockstor = sockstorRepository.findByColorAndCottonPart(color.toLowerCase(Locale.ROOT), cottonPart);
        assertThat(sockstor.getQuantity()).isEqualTo(10);
    }

    @Test
    void withdrawCottonPartOutOfBounds() throws Exception {
        String color = "red";
        byte cottonPart = 101;
        long quantity = 10;

        ResponseEntity<Object> result = TestUtils.postOutcome(color, cottonPart, quantity, restTemplate, randomServerPort);
        assertThat(result.getStatusCodeValue()).isEqualTo(400);
    }

    @Test
    void overdrawExistingTypeSocks() throws Exception {
        String color = "red";
        byte cottonPart = 50;
        long quantity = 1000;

        ResponseEntity<Object> result = TestUtils.postOutcome(color, cottonPart, quantity, restTemplate, randomServerPort);
        assertThat(result.getStatusCodeValue()).isEqualTo(400);
    }
}
