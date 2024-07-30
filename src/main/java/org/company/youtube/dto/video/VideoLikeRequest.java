package org.company.youtube.dto.video;

import org.company.youtube.enums.EmotionStatus;


public record VideoLikeRequest(
        String videoId,
        EmotionStatus status
) {

}
