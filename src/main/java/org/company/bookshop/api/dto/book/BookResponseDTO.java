package org.company.bookshop.api.dto.book;


import lombok.Builder;

import java.util.List;

@Builder
public record BookResponseDTO(
        String title,
        String description,
        int count,
        double price,
        List<String> categoryIds)
{ }