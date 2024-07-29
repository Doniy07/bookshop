package org.company.youtube.mapper;

import java.time.LocalDateTime;

public interface CommentInfoMapper {

    String getCommentId();

    String getContent();

    LocalDateTime getCreatedDate();

    Long getLikeCount();

    Long getDislikeCount();

    String getProfileId();

    String getProfileName();

    String getProfileSurname();

    String getProfilePhotoId();

    String getVideoId();

    String getVideoTitle();

    String getVideoPreviewAttachId();
}
