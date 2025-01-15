package org.company.bookshop.api.repository;

import jakarta.transaction.Transactional;
import org.company.bookshop.api.entity.SaleEntity;
import org.company.bookshop.api.mapper.SaleBookMapper;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface SaleRepository extends JpaRepository<SaleEntity, String> {

    @Query("SELECT s FROM SaleEntity s WHERE s.id = ?1 AND s.visible = true")
    Optional<SaleEntity> findByIdAndVisibleTrue(String saleId);

    @Query("SELECT s FROM SaleEntity s WHERE s.visible = true")
    List<SaleEntity> findAllByVisibleTrue();

    @Transactional
    @Modifying
    @Query("UPDATE SaleEntity s SET s.visible = false WHERE s.id = ?1")
    void delete(String saleId);

    @Query(" SELECT b.title AS bookTitle, " +
            " b.price AS bookPrice, " +
            " s.createdDate AS saleDate " +
            " FROM SaleEntity s " +
            " INNER JOIN s.book b ON s.bookId = b.id " +
            " WHERE s.customer.id = ?1 " +
            " AND s.visible = true")
    List<SaleBookMapper> findAllBooksByCustomerIdAndVisibleTrue(String id);
}
