package org.company.bookshop.api.controller;

import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.company.bookshop.api.dto.book.BookRequestDTO;
import org.company.bookshop.api.dto.book.BookResponseDTO;
import org.company.bookshop.api.service.book.BookService;
import org.company.bookshop.api.util.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/book")
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class BookController {

    BookService bookService;
    public static final String FETCH_BY_ID = "/{id}";
    public static final String CREATE = "/create";
    public static final String UPDATE = "/update/{id}";
    public static final String DELETE = "/delete/{id}";
    public static final String SEARCH = "/search";

    @GetMapping(FETCH_BY_ID)
    public ResponseEntity<ApiResponse<BookResponseDTO>> getById(
            @PathVariable("id") String bookId) {
        log.info("get book by id: {}", bookId);
        return ResponseEntity.ok(bookService.findById(bookId));
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping(CREATE)
    public ResponseEntity<ApiResponse<BookResponseDTO>> create(
            @Valid @RequestBody BookRequestDTO request) {
        return ResponseEntity.ok(bookService.save(request));
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PutMapping(UPDATE)
    public ResponseEntity<ApiResponse<BookResponseDTO>> update(
            @PathVariable("id") String bookId,
            @Valid @RequestBody BookRequestDTO request) {
        return ResponseEntity.ok(bookService.update(bookId, request));
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping(DELETE)
    public ResponseEntity<HttpStatus> delete(
            @PathVariable("id") String bookId) {
        bookService.deleteById(bookId);
        log.info("delete minivan with id: {}", bookId);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @GetMapping(SEARCH)
    public ResponseEntity<ApiResponse<List<BookResponseDTO>>> search(
            @RequestParam String title) {
        return ResponseEntity.ok().body(bookService.search(title));
    }

}