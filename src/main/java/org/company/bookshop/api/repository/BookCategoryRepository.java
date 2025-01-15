package org.company.bookshop.api.repository;

import jakarta.transaction.Transactional;
import org.company.bookshop.api.entity.BookCategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.ArrayList;
import java.util.List;

public interface BookCategoryRepository extends JpaRepository<BookCategoryEntity, String> {
    @Query(" select bc.id from BookCategoryEntity bc " +
            "where bc.bookId = ?1 ")
    List<String> findAllBookId(String bookId);

    @Transactional
    @Modifying
    @Query("DELETE FROM BookCategoryEntity bc WHERE bc.bookId = :bookId ")
    void deleteAllByBookId(@Param("bookId") String bookId);

    @Transactional
    @Modifying
    @Query("DELETE FROM BookCategoryEntity bc WHERE bc.bookId = :bookId AND bc.categoryId = :categoryId")
    void deleteByBookIdAndCategoryId(@Param("bookId") String bookId,
                                     @Param("categoryId")  String categoryId);
}
