package org.company.youtube.entity.comment;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import org.company.youtube.dto.profile.ProfileDTO;
import org.company.youtube.dto.record.comment.CommentResponse;
import org.company.youtube.enums.EmotionStatus;

import java.time.LocalDateTime;

@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public record CommentLikeResponse(
        String id,
        String profileId,
        ProfileDTO profile,
        String commentId,
        CommentResponse comment,
        EmotionStatus status,
        LocalDateTime createdDate
) {
}
