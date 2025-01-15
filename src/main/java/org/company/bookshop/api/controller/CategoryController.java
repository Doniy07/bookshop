package org.company.bookshop.api.controller;

import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.company.bookshop.api.dto.category.CategoryRequestDTO;
import org.company.bookshop.api.dto.category.CategoryRequestUpdateDTO;
import org.company.bookshop.api.dto.category.CategoryResponseDTO;
import org.company.bookshop.api.service.category.CategoryService;
import org.company.bookshop.api.util.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/category")
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class CategoryController {

    CategoryService categoryService;

    public static final String FETCH_ALL = "/all";
    public static final String FETCH_BY_ID = "/{id}";
    public static final String CREATE = "/create";
    public static final String UPDATE = "/update";
    public static final String DELETE = "/delete/{id}";

    @GetMapping(FETCH_ALL)
    public ResponseEntity<ApiResponse<List<CategoryResponseDTO>>> getAll() {
        return ResponseEntity.ok().body(categoryService.findAll());
    }

    @GetMapping(FETCH_BY_ID)
    public ResponseEntity<ApiResponse<CategoryResponseDTO>> getById(
            @PathVariable("id") String categoryId) {
//        log.info("get category by id: {}", categoryId);
        return ResponseEntity.ok(categoryService.findById(categoryId));
    }

    @PostMapping(CREATE)
    public ResponseEntity<ApiResponse<CategoryResponseDTO>> create(
            @Valid @RequestBody CategoryRequestDTO request) {
        return ResponseEntity.ok(categoryService.save(request));
    }

    @PutMapping(UPDATE)
    public ResponseEntity<ApiResponse<CategoryResponseDTO>> update(
            @RequestBody CategoryRequestUpdateDTO request) {
        return ResponseEntity.ok(categoryService.update(request));
    }

    @DeleteMapping(DELETE)
    public ResponseEntity<HttpStatus> delete(
            @PathVariable("id") String categoryId) {
        categoryService.deleteById(categoryId);
//        log.info("delete minivan with id: {}", categoryId);
        return ResponseEntity.ok(HttpStatus.OK);
    }
}