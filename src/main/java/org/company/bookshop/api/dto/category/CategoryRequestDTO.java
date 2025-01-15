package org.company.bookshop.api.dto.category;


import jakarta.validation.constraints.NotBlank;
import lombok.Builder;

@Builder
public record CategoryRequestDTO(
        @NotBlank
        String title
) {}