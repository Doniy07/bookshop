package org.company.youtube.repository.attach;


import org.company.youtube.entity.attach.AttachEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface AttachRepository extends JpaRepository<AttachEntity, String>,
        PagingAndSortingRepository<AttachEntity, String> {
}
