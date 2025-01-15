package org.company.bookshop.api.mapper;

import java.time.LocalDateTime;

public interface SaleBookMapper {
    String getBookTitle();

    double getBookPrice();

    LocalDateTime getSaleDate();
}
