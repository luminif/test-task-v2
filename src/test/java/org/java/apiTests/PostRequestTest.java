package org.java.apiTests;

import io.restassured.RestAssured;
import org.apache.http.HttpStatus;
import org.java.dto.Listing;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.UUID;
import java.util.stream.Stream;

import static org.hamcrest.Matchers.containsString;
import static org.java.specification.Specification.requestSpecification;

public class PostRequestTest extends BaseRequestTest {

    @Test
    @DisplayName("TC6 - Создание объявления со всеми обязательными полями")
    void postWithRequiredFields() {
        Listing request = createValidListing();
        UUID id = postListing(request);
        deleteListing(id);
    }

    @ParameterizedTest
    @MethodSource("provideInvalidListings")
    @DisplayName("TC7-12 - Создание объявления без обязательных полей")
    void postWithoutRequiredFields(Listing request, String expectedMessage) {
        RestAssured.given()
            .spec(requestSpecification())
            .body(request)
            .post("/api/1/item")
            .then()
            .statusCode(HttpStatus.SC_BAD_REQUEST)
            .body("result.message", containsString(expectedMessage));
    }

    static Stream<Arguments> provideInvalidListings() {
        Integer sellerId = 424242;

        return Stream.of(
            Arguments.of(
                new Listing(null, "test", 1337, new Listing.Statistics(1, 1, 1)),
                "поле sellerID обязательно"
            ),
            Arguments.of(
                new Listing(sellerId, null, 1337, new Listing.Statistics(1, 1, 1)),
                "поле name обязательно"
            ),
            Arguments.of(
                new Listing(sellerId, "test", null, new Listing.Statistics(1, 1, 1)),
                "поле price обязательно"
            ),
            Arguments.of(
                new Listing(sellerId, "test", 1337, new Listing.Statistics(null, 1, 1)),
                "поле likes обязательно"
            ),
            Arguments.of(
                new Listing(sellerId, "test", 1337, new Listing.Statistics(1, null, 1)),
                "поле viewCount обязательно"
            ),
            Arguments.of(
                new Listing(sellerId, "test", 1337, new Listing.Statistics(1, 1, null)),
                "поле contacts обязательно"
            )
        );
    }
}
