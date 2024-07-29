package org.company.youtube.dto.record.comment;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import org.company.youtube.dto.profile.ProfileDTO;
import org.company.youtube.dto.video.VideoDTO;

import java.time.LocalDateTime;

@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public record CommentResponse(

    String id,
    String profileId,
    ProfileDTO profile,
    String videoId,
    VideoDTO video,
    String content,
    String replyId,
    CommentResponse parent,
    Long likeCount,
    Long dislikeCount,
    LocalDateTime createdDate
) {
}
