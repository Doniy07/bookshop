package org.company.youtube.repository.report;

import org.company.youtube.entity.report.ReportEntity;
import org.company.youtube.mapper.RepostInfoMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ReportRepository extends JpaRepository<ReportEntity, String>,
        PagingAndSortingRepository<ReportEntity, String> {

    @Query(value = " select " +
            " r.id as id, " +
            " p.id as profileId, " +
            " p.name as profileName, " +
            " p.surname as profileSurname, " +
            " p.photoId as profilePhotoId, " +
            " r.content as content, " +
            " r.entityId as entityId, " +
            " r.type as type" +
            " from ReportEntity r " +
            " inner join r.profile p " +
            " where r.visible = true " +
            " and (?1 is null or p.id = ?1)",
            countQuery = " select count(r.id) " +
                    " from ReportEntity r " +
                    " inner join r.profile p " +
                    " where r.visible = true " +
                    " and (?1 is null or p.id = ?1)")
    Page<RepostInfoMapper> findAllBy(String profileId, Pageable pageable);

    Optional<ReportEntity> findByIdAndVisibleTrue(String reportId);
}
