package org.company.bookshop.api.repository;

import jakarta.transaction.Transactional;
import org.company.bookshop.api.entity.CategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<CategoryEntity, Long> {

    @Query("SELECT g FROM CategoryEntity g WHERE g.title = ?1 AND g.visible = true")
    Optional<CategoryEntity> findByTitle(String title);

    @Query("SELECT g FROM CategoryEntity g WHERE g.id = ?1 AND g.visible = true ")
    Optional<CategoryEntity> findById(String categoryId);

    @Transactional
    @Modifying
    @Query(" UPDATE CategoryEntity g SET g.visible = false WHERE g.id = ?1 ")
    void deleteById(String categoryId);

    @Query("SELECT g FROM CategoryEntity g WHERE g.visible = true")
    List<CategoryEntity> findAllByVisibleTrue();
}
