package org.company.youtube.repository.video;

import jakarta.transaction.Transactional;
import org.company.youtube.entity.video.VideoTagEntity;
import org.company.youtube.mapper.VideoTagMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VideoTagRepository extends JpaRepository<VideoTagEntity, String> {


    @Query(" select t.name from VideoTagEntity as vt " +
            " inner join vt.tag as t" +
            " where vt.videoId = ?1 ")
    List<String> findAllTagsIdByVideoId(String videoId);


    @Transactional
    @Modifying
    @Query("DELETE FROM VideoTagEntity vt WHERE vt.videoId = :videoId AND vt.tagId IN :taglist")
    void deleteAllByVideoIdAndTagList(@Param("videoId") String videoId, @Param("taglist") List<String> taglist);

    @Transactional
    @Modifying
    void deleteByVideoIdAndTagId(String videoId, String tagId);

//    (id,video_id,tag(id,name),created_date)
    @Query(" select " +
            " vt.id, " +
            " vt.videoId, " +
            " vt.tagId," +
            " t.name as tagName, " +
            " vt.createdDate " +
            " from VideoTagEntity vt " +
            " inner join vt.tag t " +
            " where vt.videoId = ?1")
    Page<VideoTagMapper> findByVideoId(String videoId, Pageable pageable);
}
