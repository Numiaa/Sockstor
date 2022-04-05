package me.numiaa.sockstortest;

import me.numiaa.sockstor.SockstorApplication;
import me.numiaa.sockstor.domain.Operation;
import me.numiaa.sockstortest.utils.TestUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(classes = SockstorApplication.class, webEnvironment=SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource("/application-test.properties")
@Sql(value = {"/query/add-stor-before.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(value = {"/query/add-stor-after.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
public class GetQuantityTest {

    @LocalServerPort
    int randomServerPort;

    @Autowired
    TestRestTemplate restTemplate;

    @Test
    void getEqual() throws Exception {
        String color = "red";
        byte cottonPart = 50;
        Operation operation = Operation.equal;

        ResponseEntity<Object> result = TestUtils.getSockStockQuantity(color, cottonPart, operation, restTemplate, randomServerPort);
        assertThat(result.getStatusCodeValue()).isEqualTo(200);
        assertThat(result.getBody()).isEqualTo(11);
    }

    @Test
    void getMoreThan() throws Exception {
        String color = "red";
        byte cottonPart = 49;
        Operation operation = Operation.moreThan;

        ResponseEntity<Object> result = TestUtils.getSockStockQuantity(color, cottonPart, operation, restTemplate, randomServerPort);
        assertThat(result.getStatusCodeValue()).isEqualTo(200);
        assertThat(result.getBody()).isEqualTo(21);
    }

    @Test
    void getLessThan() throws Exception {
        String color = "blue";
        byte cottonPart = 101;
        Operation operation = Operation.lessThan;

        ResponseEntity<Object> result = TestUtils.getSockStockQuantity(color, cottonPart, operation, restTemplate, randomServerPort);
        assertThat(result.getStatusCodeValue()).isEqualTo(200);
        assertThat(result.getBody()).isEqualTo(13);
    }

    @Test
    void getByUniqueColor() throws Exception {
        String color = "black";
        byte cottonPart = 1;
        Operation operation = Operation.equal;

        ResponseEntity<Object> result = TestUtils.getSockStockQuantity(color, cottonPart, operation, restTemplate, randomServerPort);
        assertThat(result.getStatusCodeValue()).isEqualTo(200);
        assertThat(result.getBody()).isEqualTo(0);
    }

    @Test
    void getByUpperCaseColor() throws Exception {
        String color = "RED";
        byte cottonPart = -1;
        Operation operation = Operation.moreThan;

        ResponseEntity<Object> result = TestUtils.getSockStockQuantity(color, cottonPart, operation, restTemplate, randomServerPort);
        assertThat(result.getStatusCodeValue()).isEqualTo(200);
        assertThat(result.getBody()).isEqualTo(21);
    }
}