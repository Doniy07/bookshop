package org.company.youtube.mapper;

import org.company.youtube.enums.EmotionStatus;

import java.time.LocalDateTime;

public interface CommentLikeInfoMapper {

//    id,profile_id,comment_id,
//    created_date,type(Like,Dislike)

    String getId();

    String getProfileId();

    String getCommentId();

    EmotionStatus getStatus();

    LocalDateTime getCreatedDate();
}
