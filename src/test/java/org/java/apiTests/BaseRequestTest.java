package org.java.apiTests;

import io.restassured.RestAssured;
import org.apache.http.HttpStatus;
import org.java.dto.Listing;

import java.util.UUID;

import static org.java.specification.Specification.requestSpecification;

public abstract class BaseRequestTest {
    protected Integer sellerId = 424242;

    protected UUID postListing(Listing listing) {
        String statusMessage = RestAssured.given()
            .spec(requestSpecification())
            .body(listing)
            .post("/api/1/item")
            .then()
            .statusCode(HttpStatus.SC_OK)
            .extract()
            .path("status");
        return UUID.fromString(statusMessage.replace("Сохранили объявление - ", "").trim());
    }

    protected void deleteListing(UUID id) {
        RestAssured.given()
            .spec(requestSpecification())
            .delete("/api/2/item/{id}", id);
    }

    protected Listing createValidListing() {
        return new Listing(
            sellerId,
            "test",
            1337,
            new Listing.Statistics(1, 2, 3)
        );
    }
}
