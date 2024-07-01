package org.company.youtube.repository.profile;

import jakarta.transaction.Transactional;
import org.company.youtube.entity.profile.ProfileEntity;
import org.company.youtube.enums.ProfileStatus;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface ProfileRepository extends CrudRepository<ProfileEntity, String> {
    Optional<ProfileEntity> findByEmail(String email);

    @Transactional
    @Modifying
    @Query("update ProfileEntity set status =?2 where id =?1")
    void updateStatus(String profileId, ProfileStatus status);

    Optional<ProfileEntity> findByTempEmail(String email);

    @Transactional
    @Modifying
    @Query("update ProfileEntity set email =?2 where id =?1")
    void updateEmail(String profileId, String email);
}
