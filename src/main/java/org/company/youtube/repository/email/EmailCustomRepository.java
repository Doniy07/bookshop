package org.company.youtube.repository.email;



import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import lombok.RequiredArgsConstructor;
import org.company.youtube.dto.email.EmailFilterDTO;
import org.company.youtube.dto.filter.FilterResponseDTO;
import org.company.youtube.entity.email.EmailEntity;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
@RequiredArgsConstructor
public class EmailCustomRepository {

    private final EntityManager em;

    public FilterResponseDTO<EmailEntity> filter(EmailFilterDTO filter, int page, int size) {
        Map<String, Object> params = new HashMap<>();

        StringBuilder query = new StringBuilder();
        if (filter.getEmail() != null) {
            query.append(" and e.to_email=:email ");
            params.put("email", filter.getEmail());
        }
        if (filter.getTo() != null) {
            query.append(" and p.created_date >=: to ");
            params.put("to", filter.getTo());
        }
        if (filter.getFrom() != null) {
            query.append(" and p.created_date <=: from ");
            params.put("from", filter.getFrom());
        }

        StringBuilder selectSql = new StringBuilder("From EmailEntity e where e.visible=true ");
        selectSql.append(query);
        StringBuilder counttSdl = new StringBuilder("select count (e) From EmailEntity e where e.visible=true ");
        counttSdl.append(query);

        Query selectQuery = em.createQuery(selectSql.toString());
        Query countQuery = em.createQuery(counttSdl.toString());

        for (Map.Entry<String, Object> entity : params.entrySet()) {
            selectQuery.setParameter(entity.getKey(), entity.getValue());
            countQuery.setParameter(entity.getKey(), entity.getValue());
        }

        selectQuery.setFirstResult(page * size);
        selectQuery.setMaxResults(size);
        List<EmailEntity> list = selectQuery.getResultList();

        Long totalCount =(Long) countQuery.getSingleResult();

        return new FilterResponseDTO<EmailEntity>(list,totalCount);
    }
}
