package org.company.youtube.dto.record.subscription;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import org.company.youtube.dto.channel.ChannelDTO;
import org.company.youtube.dto.profile.ProfileDTO;
import org.company.youtube.enums.SubscriptionNotificationType;
import org.company.youtube.enums.SubscriptionStatus;

import java.time.LocalDateTime;

@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public record SubscriptionResponse(
        String id,
        String profileId,
        ProfileDTO profile,
        String channelId,
        ChannelDTO channel,
        LocalDateTime createdDate,
        LocalDateTime unsubscribeDate,
        SubscriptionStatus status,
        SubscriptionNotificationType type
) {
}
