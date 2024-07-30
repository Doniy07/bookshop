package org.company.youtube.dto.video;

import lombok.Builder;
import org.company.youtube.dto.profile.ProfileDTO;
import org.company.youtube.enums.EmotionStatus;

import java.time.LocalDateTime;

@Builder
public record VideoLikeResponse(
        String id,
        String videoId,
        VideoDTO video,
        String profileId,
        ProfileDTO profile,
        EmotionStatus status,
        LocalDateTime createdDate
) {
}
