package org.java.specification;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;

import static org.java.utils.Utils.getBaseUrl;

public class Specification {
    public static RequestSpecification requestSpecification() {
        return new RequestSpecBuilder()
            .setBaseUri(getBaseUrl())
            .setRelaxedHTTPSValidation()
            .setContentType(ContentType.JSON)
            .setAccept(ContentType.JSON)
            .build();
    }
}
