package org.company.bookshop.api.service.book;


import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.company.bookshop.api.dto.book.BookRequestDTO;
import org.company.bookshop.api.dto.book.BookResponseDTO;
import org.company.bookshop.api.entity.BookEntity;
import org.company.bookshop.api.exception.BadRequestException;
import org.company.bookshop.api.repository.BookRepository;
import org.company.bookshop.api.service.BookCategoryService;
import org.company.bookshop.api.util.ApiResponse;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.function.Function;

@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class BookServiceImpl implements BookService {

    BookRepository bookRepository;
    BookCategoryService bookCategoryService;

    @Override
    public ApiResponse<BookResponseDTO> findById(String bookId) {
        log.info("get book by id: {}", bookId);
        return ApiResponse.ok(mapToResponse().apply(getById(bookId)));
    }

    @Override
    public ApiResponse<BookResponseDTO> save(BookRequestDTO request) {
        if (request.count() <= 0) throw new BadRequestException("Count must be greater than 0");
        BookEntity entity = bookRepository.save(mapToEntity().apply(request));
        bookCategoryService.merge(entity.getId(), request.categoryIds());
        log.info("Book created successfully with title: {}", request.title());
        return ApiResponse.ok();
    }

    @Override
    public ApiResponse<BookResponseDTO> update(String bookId, BookRequestDTO request) {
        BookEntity entity = getById(bookId);
        if (request.count() <= 0) throw new BadRequestException("Count must be greater than 0");
        entity.setTitle(request.title());
        entity.setDescription(request.description());
        entity.setPrice(request.price());
        entity.setCount(request.count());

        bookRepository.save(entity);
        bookCategoryService.merge(entity.getId(), request.categoryIds());

        log.info("Book updated successfully with title: {}", request.title());
        return ApiResponse.ok(mapToResponse().apply(entity));
    }


    @Override
    public void deleteById(String bookId) {
        log.info("Book deleted successfully with id: {}", bookId);
        bookRepository.deleteBookById(bookId);
        bookCategoryService.deleteAllByBookId(bookId);
    }

    private Function<BookEntity, BookResponseDTO> mapToResponse() {
        return entity -> BookResponseDTO.builder()
                .title(entity.getTitle())
                .description(entity.getDescription())
                .count(entity.getCount())
                .price(entity.getPrice())
                .build();
    }

    private Function<BookRequestDTO, BookEntity> mapToEntity() {
        return request -> BookEntity.builder()
                .title(request.title())
                .description(request.description())
                .count(request.count())
                .price(request.price())
                .build();
    }


    @Override
    public ApiResponse<List<BookResponseDTO>> search(String title) {
        List<BookEntity> entities = bookRepository.searchBookByTitle(title);
        List<BookResponseDTO> responses = entities.
                stream()
                .map(entity -> BookResponseDTO.builder()
                        .title(entity.getTitle())
                        .description(entity.getDescription())
                        .price(entity.getPrice())
                        .count(entity.getCount())
                        .categoryIds(bookCategoryService.findAllBookId(entity.getId()))
                        .build()
                )
                .toList();

        return ApiResponse.ok(responses);
    }

    public BookEntity getById(String bookId) {
        return bookRepository.findByIdAndVisibleTrue(bookId).orElseThrow(() -> {
            log.warn("Book with id {} not found", bookId);
            return new BadRequestException("Book not found");
        });
    }

    public void updateCount(String bookId, Integer count) {
        bookRepository.updateCount(count, bookId);
    }
}



