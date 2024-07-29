package org.company.youtube.repository.comment;

import org.company.youtube.entity.comment.CommentEntity;
import org.company.youtube.mapper.CommentInfoMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CommentRepository extends JpaRepository<CommentEntity, String>,
        PagingAndSortingRepository<CommentEntity, String> {

    Optional<CommentEntity> findByIdAndVisibleTrue(String id);

    @Query("  select" +
            " c.id as commentId, " +
            " c.content as content, " +
            " c.createdDate as createdDate, " +
            " c.likeCount as likeCount, " +
            " c.dislikeCount as dislikeCount, " +
            " v.id as videoId, " +
            " v.title as videoTitle, " +
            " v.previewAttachId as videoPreviewAttachId " +
            " from CommentEntity c" +
            " inner join c.video v " +
            " where c.profileId = ?1 and (?2 is null or c.visible = ?2) " +
            " order by c.createdDate desc")
    Page<CommentInfoMapper> findAllByProfileIdAndVisible(String profileId, Boolean visible, Pageable pageable);
    //    id,content,created_date,like_count,dislike_count, video(id,name,preview_attach_id,title,duration)

    @Query("  select" +
            " c.id as commentId, " +
            " c.content as content, " +
            " c.createdDate as createdDate, " +
            " c.likeCount as likeCount, " +
            " c.dislikeCount as dislikeCount, " +
            " v.id as videoId, " +
            " v.title as videoTitle, " +
            " v.previewAttachId as videoPreviewAttachId " +
            " from CommentEntity c" +
            " inner join c.video v " +
            " where c.videoId = ?1 and c.visible = true " +
            " order by c.createdDate desc")
    Page<CommentInfoMapper> findAllByVideoIdAndVisibleTrue(String videoId, Pageable pageable);
//    id,content,created_date,like_count,dislike_count, video(id,name,preview_attach_id,title,duration)

    @Query("  select" +
            " c.id as commentId, " +
            " c.content as content, " +
            " c.createdDate as createdDate, " +
            " c.likeCount as likeCount, " +
            " c.dislikeCount as dislikeCount, " +
            " v.id as videoId, " +
            " v.title as videoTitle, " +
            " v.previewAttachId as videoPreviewAttachId " +
            " from CommentEntity c" +
            " inner join c.video v " +
            " where c.replyId = ?1 and c.visible = true " +
            " order by c.createdDate desc")
    Page<CommentInfoMapper> findAllByReplyIdAndVisibleTrue(String replyId, Pageable pageable);


}
