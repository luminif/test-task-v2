package org.java.apiTests;

import io.restassured.RestAssured;
import org.apache.http.HttpStatus;
import org.hamcrest.Matchers;
import org.java.dto.Listing;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.hamcrest.Matchers.equalTo;
import static org.java.specification.Specification.requestSpecification;

public class GetRequestTest extends BaseRequestTest {
    private Listing request;
    private UUID id;

    @BeforeEach
    void setUp() {
        request = createValidListing();
        id = postListing(request);
    }

    @AfterEach
    void tearDown() {
        deleteListing(id);
    }

    @Test
    @DisplayName("TC1 - Получение существующего объявления")
    void getListingsById() {
        RestAssured.given()
            .spec(requestSpecification())
            .get("/api/1/item/{id}", id)
            .then()
            .statusCode(HttpStatus.SC_OK)
            .assertThat()
            .body("[0].name", equalTo(request.name()))
            .body("[0].price", equalTo(request.price()))
            .body("[0].sellerId", equalTo(request.sellerId()))
            .body("[0].statistics.contacts", equalTo(request.statistics().contacts()))
            .body("[0].statistics.likes", equalTo(request.statistics().likes()))
            .body("[0].statistics.viewCount", equalTo(request.statistics().viewCount()));
    }

    @Test
    @DisplayName("TC2 - Получение несуществующего объявления")
    void getListingsByUnknownId() {
        RestAssured.given()
            .spec(requestSpecification())
            .get("/api/1/item/{id}", UUID.randomUUID())
            .then()
            .statusCode(HttpStatus.SC_NOT_FOUND);
    }

    @Test
    @DisplayName("TC3 - Получение всех объявлений продавца по его идентификатору")
    void getListingsBySellerId() {
        RestAssured.given()
            .spec(requestSpecification())
            .get("/api/1/{sellerID}/item", sellerId)
            .then()
            .statusCode(HttpStatus.SC_OK)
            .assertThat()
            .body("size()", Matchers.greaterThanOrEqualTo(1));
    }
}
