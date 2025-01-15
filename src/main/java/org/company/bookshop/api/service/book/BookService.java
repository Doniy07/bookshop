package org.company.bookshop.api.service.book;


import org.company.bookshop.api.dto.book.BookRequestDTO;
import org.company.bookshop.api.dto.book.BookResponseDTO;
import org.company.bookshop.api.util.ApiResponse;

import java.util.List;

public interface BookService {

    ApiResponse<BookResponseDTO> findById(String bookId);

    ApiResponse<BookResponseDTO> save(BookRequestDTO request);

    ApiResponse<BookResponseDTO> update(String bookId, BookRequestDTO request);

    void deleteById(String bookId);

    ApiResponse<List<BookResponseDTO>> search(String title);

}