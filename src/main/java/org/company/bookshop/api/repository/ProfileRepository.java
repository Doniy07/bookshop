package org.company.bookshop.api.repository;


import jakarta.transaction.Transactional;
import org.company.bookshop.api.entity.ProfileEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface ProfileRepository extends JpaRepository<ProfileEntity, String> {

    Optional<ProfileEntity> findByPhoneAndVisibleTrue(String phone);

    Optional<ProfileEntity> findByIdAndVisibleTrue(String profileId);

    @Transactional
    @Modifying
    @Query("UPDATE ProfileEntity p SET p.visible = false WHERE p.id = ?1")
    void deleteProfileById(String profileId);

    @Query("SELECT p FROM ProfileEntity p WHERE p.visible = true")
    List<ProfileEntity> findAllByVisibleTrue();
    @Transactional
    @Modifying
    @Query("UPDATE ProfileEntity p SET p.balance = ?2 WHERE p.id = ?1")
    void updateBalance(String profileId, Double balance);
}
