package org.java.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record Listing(
    @JsonProperty("sellerID")
    Integer sellerId,
    String name,
    Integer price,
    Statistics statistics
) {
    public record Statistics(
        Integer likes,
        Integer viewCount,
        Integer contacts
    ) {
    }
}
