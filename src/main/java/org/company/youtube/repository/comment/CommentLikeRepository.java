package org.company.youtube.repository.comment;

import org.company.youtube.entity.comment.CommentLikeEntity;
import org.company.youtube.mapper.CommentLikeInfoMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.Optional;

public interface CommentLikeRepository extends JpaRepository<CommentLikeEntity, String>,
        PagingAndSortingRepository<CommentLikeEntity, String> {

    Optional<CommentLikeEntity> findByCommentIdAndProfileId(String commentId, String profileId);

    @Query("SELECT " +
            "cl.id AS id, " +
            "cl.commentId AS commentId, " +
            "cl.profileId AS profileId, " +
            "cl.status AS status, " +
            "cl.createdDate AS createdDate " +
            "FROM CommentLikeEntity cl " +
            "WHERE cl.status = 'LIKE' AND cl.profileId = ?1")
    Page<CommentLikeInfoMapper> findAllByProfileId(String profileId, Pageable pageable);



}
