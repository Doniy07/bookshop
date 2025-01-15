package org.company.bookshop.api.service.sale;

import org.company.bookshop.api.dto.sale.SaleCustomerResponseDTO;
import org.company.bookshop.api.dto.sale.SaleRequestDTO;
import org.company.bookshop.api.dto.sale.SaleResponseDTO;
import org.company.bookshop.api.util.ApiResponse;

import java.util.List;

public interface SaleService {

    ApiResponse<SaleResponseDTO> save(SaleRequestDTO request);

    ApiResponse<SaleResponseDTO> findById(String saleId);

    ApiResponse<List<SaleResponseDTO>> findAll();

    void delete(String saleId);

    ApiResponse<List<SaleCustomerResponseDTO>> myPurchased();
}
