package org.company.youtube.repository.email;


import org.company.youtube.entity.email.EmailEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface EmailRepository extends CrudRepository<EmailEntity, String>,
        PagingAndSortingRepository<EmailEntity, String> {

    Optional<EmailEntity> findTop1ByToEmailOrderByCreatedDateDesc(String email);

    long countByToEmailAndCreatedDateBetween(String email, LocalDateTime from, LocalDateTime to);

    Page<EmailEntity> findAllByToEmail(String email, Pageable pageable);
}
