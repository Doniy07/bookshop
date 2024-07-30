package org.company.youtube.entity.comment;

import org.company.youtube.enums.EmotionStatus;

public record CommentLikeRequest(
    String commentId,
    EmotionStatus status
) {
}
