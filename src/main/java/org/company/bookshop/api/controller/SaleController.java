package org.company.bookshop.api.controller;

import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.company.bookshop.api.dto.sale.SaleCustomerResponseDTO;
import org.company.bookshop.api.dto.sale.SaleRequestDTO;
import org.company.bookshop.api.dto.sale.SaleResponseDTO;
import org.company.bookshop.api.service.sale.SaleService;
import org.company.bookshop.api.util.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/sale")
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class SaleController {

    SaleService saleService;

    public static final String FETCH_ALL = "/all";
    public static final String FETCH_BY_ID = "/{id}";
    public static final String MY_PURCHASED_BOOKS = "/my-purchased-books";
    public static final String CREATE = "/create";
    public static final String DELETE = "/delete/{id}";

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping(FETCH_ALL)
    public ResponseEntity<ApiResponse<List<SaleResponseDTO>>> getAll() {
        return ResponseEntity.ok().body(saleService.findAll());
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping(FETCH_BY_ID)
    public ResponseEntity<ApiResponse<SaleResponseDTO>> getById(
            @PathVariable("id") String saleId) {
        return ResponseEntity.ok().body(saleService.findById(saleId));
    }

    @PostMapping(CREATE)
    public ResponseEntity<ApiResponse<SaleResponseDTO>> create(
            @Valid @RequestBody SaleRequestDTO request) {
        return ResponseEntity.ok(saleService.save(request));
    }

    @GetMapping(MY_PURCHASED_BOOKS)
    public ResponseEntity<ApiResponse<List<SaleCustomerResponseDTO>>> myPurchased() {
        return ResponseEntity.ok(saleService.myPurchased());
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping(DELETE)
    public ResponseEntity<HttpStatus> delete(
            @PathVariable("id") String saleId) {
        saleService.delete(saleId);
//        log.info("delete minivan with id: {}", categoryId);
        return ResponseEntity.ok(HttpStatus.OK);
    }


}
