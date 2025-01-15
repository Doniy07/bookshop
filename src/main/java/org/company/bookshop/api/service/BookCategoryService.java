package org.company.bookshop.api.service;

import jakarta.transaction.Transactional;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.company.bookshop.api.entity.BookCategoryEntity;
import org.company.bookshop.api.repository.BookCategoryRepository;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class BookCategoryService {

    BookCategoryRepository bookCategoryRepository;

    @Transactional
    public void merge(String bookId, List<String> newCategories) {
        Objects.requireNonNull(newCategories, "Category list must not be null");

        // Получаем текущие категории из базы данных
        List<String> oldCategories = bookCategoryRepository.findAllBookId(bookId);

        // Удаляем категории, которых нет в новом списке
        oldCategories.forEach(oldCategory -> {
            if (!newCategories.contains(oldCategory)) {
                bookCategoryRepository.deleteByBookIdAndCategoryId(bookId, oldCategory);
            }
        });

        // Добавляем категории, которых нет в старом списке
        newCategories.forEach(newCategory -> {
            if (!oldCategories.contains(newCategory)) {
                create(bookId, newCategory);
            }
        });
    }

    // Метод для создания одной категории
    private void create(String bookId, String categoryId) {
        BookCategoryEntity entity = new BookCategoryEntity(bookId, categoryId);
        bookCategoryRepository.save(entity);
    }


    public void delete(String bookId, String categoryId) {
        bookCategoryRepository.deleteByBookIdAndCategoryId(bookId, categoryId);
    }


    public List<String> findAllBookId(String bookId) {
        return bookCategoryRepository.findAllBookId(bookId);
    }

    public void deleteAllByBookId(String bookId) {
        bookCategoryRepository.deleteAllByBookId(bookId);
    }
}
