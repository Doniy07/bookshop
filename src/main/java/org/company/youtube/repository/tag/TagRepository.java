package org.company.youtube.repository.tag;

import org.company.youtube.entity.tag.TagEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface TagRepository extends CrudRepository<TagEntity, Integer>,
        PagingAndSortingRepository<TagEntity, Integer> {
}
