package org.company.bookshop.api.dto.sale;

import lombok.Builder;

@Builder
public record SaleResponseDTO (
        String bookId,
        String customerId
){ }
