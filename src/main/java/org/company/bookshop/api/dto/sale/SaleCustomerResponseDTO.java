package org.company.bookshop.api.dto.sale;

import lombok.Builder;

import java.time.LocalDate;

@Builder
public record SaleCustomerResponseDTO(
        String bookTitle,
        double bookPrice,
        LocalDate saleDate
) {
}
