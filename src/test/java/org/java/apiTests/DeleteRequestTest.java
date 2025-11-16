package org.java.apiTests;

import io.restassured.RestAssured;
import org.apache.http.HttpStatus;
import org.java.dto.Listing;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.java.specification.Specification.requestSpecification;

public class DeleteRequestTest extends BaseRequestTest {

    @Test
    @DisplayName("TC4 - Удаление существующего объявления")
    void deleteListingById() {
        Listing request = createValidListing();
        UUID id = postListing(request);

        RestAssured.given()
            .spec(requestSpecification())
            .delete("/api/2/item/{id}", id)
            .then()
            .statusCode(HttpStatus.SC_OK);
    }

    @Test
    @DisplayName("TC5 - Удаление несуществующего объявления")
    void deleteListingByUnknownId() {
        RestAssured.given()
            .spec(requestSpecification())
            .delete("/api/2/item/{id}", UUID.randomUUID())
            .then()
            .statusCode(HttpStatus.SC_NOT_FOUND);
    }
}
