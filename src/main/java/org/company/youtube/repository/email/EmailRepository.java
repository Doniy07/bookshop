package org.company.youtube.repository.email;


import org.company.youtube.entity.email.EmailEntity;
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

//    @Query(value = "select id, to_email,title, message, date(created_date) as created_date from email_history where created_date between ?1 and ?2", nativeQuery = true)
//    List<EmailEntity> findByCreatedDate(LocalDate fromDate, LocalDate toDate);

    long countByToEmailAndCreatedDateBetween(String email, LocalDateTime from, LocalDateTime to);

//    List<EmailEntity> findAllByEmail(String email);
}
