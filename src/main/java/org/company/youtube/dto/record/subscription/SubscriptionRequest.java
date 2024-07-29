package org.company.youtube.dto.record.subscription;

import org.company.youtube.enums.SubscriptionNotificationType;
import org.company.youtube.enums.SubscriptionStatus;

public record SubscriptionRequest(
        String channelId,
        SubscriptionNotificationType type
) {
    public record ChangeStatus(
            String channelId,
            SubscriptionStatus status
    ){}
}
