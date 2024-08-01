package org.company.youtube.repository.video;

import jakarta.transaction.Transactional;
import org.company.youtube.entity.video.VideoEntity;
import org.company.youtube.mapper.VideoShortInfoMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface VideoRepository extends JpaRepository<VideoEntity, String>,
        PagingAndSortingRepository<VideoEntity, String> {

    @Transactional
    @Modifying
    @Query("UPDATE VideoEntity set viewCount = COALESCE(viewCount,0) + 1 where id =?1")
    void increaseViewCount(String articleId);

    @Transactional
    @Modifying
    @Query("UPDATE VideoEntity set sharedCount = COALESCE(sharedCount,0) + 1 where id =?1")
    void increaseShareCount(String articleId);

    @Query(value = "  SELECT v.id AS videoId," +
            " v.title AS videoTitle, " +
            " v.previewAttachId AS videoPreviewAttachId, " +
            " v.publishedDate AS videoPublishedDate, " +
            " c.id AS channelId," +
            " c.name AS channelName," +
            " c.photoId AS channelPhotoId, " +
            " v.viewCount AS videoViewCount " +
            " FROM VideoEntity v " +
            " INNER JOIN v.tags vt " +
            " INNER JOIN v.channel c " +
            " WHERE v.categoryId = ?1 " +
            " ORDER BY v.createdDate DESC ",
            countQuery = " SELECT count(v.id) " +
                    " FROM VideoEntity v " +
                    " INNER JOIN v.tags vt " +
                    " INNER JOIN v.channel c " +
                    " WHERE v.categoryId = ?1 ")
    Page<VideoShortInfoMapper> findByCategoryId(String categoryId, Pageable pageable);

    @Query(value = "  SELECT v.id AS videoId," +
            " v.title AS videoTitle, " +
            " v.previewAttachId AS videoPreviewAttachId, " +
            " v.publishedDate AS videoPublishedDate, " +
            " c.id AS channelId," +
            " c.name AS channelName," +
            " c.photoId AS channelPhotoId, " +
            " v.viewCount AS videoViewCount " +
            " FROM VideoEntity v " +
            " INNER JOIN v.tags vt " +
            " INNER JOIN v.channel c " +
            " WHERE v.title = ?1 " +
            " ORDER BY v.createdDate DESC ",
            countQuery = " SELECT count(v.id) " +
                    " FROM VideoEntity v " +
                    " INNER JOIN v.tags vt " +
                    " INNER JOIN v.channel c " +
                    " WHERE v.title = ?1 ")
    Page<VideoShortInfoMapper> findByTitle(String title, Pageable pageable);

    @Query(value = "  SELECT v.id AS videoId," +
            " v.title AS videoTitle, " +
            " v.previewAttachId AS videoPreviewAttachId, " +
            " v.publishedDate AS videoPublishedDate, " +
            " c.id AS channelId," +
            " c.name AS channelName," +
            " c.photoId AS channelPhotoId, " +
            " v.viewCount AS videoViewCount " +
            " FROM VideoEntity v " +
            " INNER JOIN v.tags vt " +
            " INNER JOIN v.channel c " +
            " WHERE vt.tagId = ?1",
            countQuery = " SELECT count(v.id) " +
                    " FROM VideoEntity v " +
                    " INNER JOIN v.tags vt " +
                    " INNER JOIN v.channel c " +
                    " WHERE vt.tagId = ?1")
    Page<VideoShortInfoMapper> findByTagId(String tagId, Pageable pageable);


    //    VideoFullInfo (id,title,description,
//                preview_attach(id,url),attach(id,url,duration),
//                category(id,name),tagList(id,name),
//                published_date, channel(id,name,photo(url)),
//                view_count,shared_count,Like(like_count,dislike_count,
//                isUserLiked,IsUserDisliked),duration)
    @Query("  SELECT " +
            " v.id AS videoId," +
            " v.title AS videoTitle, " +
            " v.previewAttachId AS videoPreviewAttachId, " +
            " v.videoId, " +
            " ct.id AS categoryId, " +
            " ct.name AS categoryName, " +
            " json_agg(json_build_object( " +
            "        'id', t.id, " +
            "        'title', t.name " +
            "    )) AS tagList, " +
            " v.publishedDate AS videoPublishedDate, " +
            " ch.id AS channelId," +
            " ch.name AS channelName," +
            " ch.photoId AS channelPhotoId, " +
            " v.viewCount AS videoViewCount, " +
            " v.sharedCount AS videoSharedCount " +
            " FROM VideoEntity v " +
            " INNER JOIN v.tags vt " +
            " INNER JOIN vt.tag t " +
            " INNER JOIN v.channel ch " +
            " INNER JOIN v.category ct " +
            " WHERE v.id = ?1 " +
            " GROUP BY v.id, ct.id, ch.id ")
    // TODO: add isUserLiked, IsUserDisliked when created video_like table
    List<Object[]> findByVideoId(@Param("videoId") String videoId);

    //     VideoShortInfo(id,title, preview_attach(id,url),
//                   published_date, channel(id,name,photo(url)),
//                   view_count,duration)
    @Query(value = "  SELECT v.id AS videoId," +
            " v.title AS videoTitle, " +
            " v.previewAttachId AS videoPreviewAttachId, " +
            " v.publishedDate AS videoPublishedDate, " +
            " c.id AS channelId," +
            " c.name AS channelName," +
            " c.photoId AS channelPhotoId, " +
            " v.viewCount AS videoViewCount " +
            " FROM VideoEntity v " +
            " INNER JOIN v.tags vt " +
            " INNER JOIN v.channel c " +
            " ORDER BY v.createdDate DESC ",
            countQuery = " SELECT count(v.id) " +
                    " FROM VideoEntity v " +
                    " INNER JOIN v.tags vt " +
                    " INNER JOIN v.channel c ")
    Page<VideoShortInfoMapper> findAllBy(Pageable pageable);

    //    VideoPlayListInfo(id,title, preview_attach(id,url), view_count,
//                       published_date,duration)
    @Query(value = "  SELECT v.id AS videoId," +
            " v.title AS videoTitle, " +
            " v.previewAttachId AS videoPreviewAttachId, " +
            " v.publishedDate AS videoPublishedDate, " +
            " c.id AS channelId," +
            " c.name AS channelName," +
            " c.photoId AS channelPhotoId, " +
            " v.viewCount AS videoViewCount " +
            " FROM VideoEntity v " +
            " INNER JOIN v.tags vt " +
            " INNER JOIN v.channel c " +
            " WHERE v.channelId = ?1 " +
            " ORDER BY v.createdDate DESC ",
            countQuery = " SELECT count(v.id) " +
                    " FROM VideoEntity v " +
                    " INNER JOIN v.tags vt " +
                    " INNER JOIN v.channel c " +
                    " WHERE v.channelId = ?1 ")
    Page<VideoShortInfoMapper> findByChannelId(String channelId, Pageable pageable);

    Optional<VideoEntity> findByIdAndVisibleTrue(String videoId);
}
