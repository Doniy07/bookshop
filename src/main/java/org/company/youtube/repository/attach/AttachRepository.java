package org.company.youtube.repository.attach;


import org.company.youtube.entity.attach.AttachEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface AttachRepository extends CrudRepository<AttachEntity, String>,
        PagingAndSortingRepository<AttachEntity, String> {
}
