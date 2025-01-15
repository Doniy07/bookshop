package org.company.bookshop.api.dto.book;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.Builder;

import java.util.List;

@Builder
public record BookRequestDTO(

        @NotBlank
        String title,
        String description,
        @Positive
        int count,
        double price,
        List<String> categoryIds
) {
}