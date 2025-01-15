package org.company.bookshop.api.repository;


import jakarta.transaction.Transactional;
import org.company.bookshop.api.entity.BookEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BookRepository extends JpaRepository<BookEntity, String> {

    @Query("SELECT b FROM BookEntity b WHERE b.id = ?1 AND b.visible = true AND b.count > 0")
    Optional<BookEntity> findByIdAndVisibleTrue(String id);

    @Transactional
    @Modifying
    @Query("UPDATE BookEntity b SET b.visible = false WHERE b.id = ?1")
    void deleteBookById(String bookId);

    @Query(" FROM BookEntity b " +
            " WHERE b.visible = true " +
            " AND (:title IS NULL OR LOWER(b.title) LIKE LOWER(CONCAT('%', :title, '%'))) " +
            " AND b.count > 0" +
            " ORDER BY b.createdDate DESC ")
    List<BookEntity> searchBookByTitle(@Param("title") String title);


    @Transactional
    @Modifying
    @Query("UPDATE BookEntity b SET b.count = ?1 WHERE b.id = ?2")
    void updateCount(Integer count, String bookId);
}


