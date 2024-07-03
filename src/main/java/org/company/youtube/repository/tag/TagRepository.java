package org.company.youtube.repository.tag;

import org.company.youtube.entity.tag.TagEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface TagRepository extends CrudRepository<TagEntity, Integer>,
        PagingAndSortingRepository<TagEntity, Integer> {

    @Query("select t.name from TagEntity as t")
    List<String> findAllName();

    List<TagEntity> findAllByNameIn(List<String> tagNames);
}
