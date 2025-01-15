package org.company.bookshop.api.dto.category;


import lombok.Builder;

@Builder
public record CategoryResponseDTO(
        String title
) {
}