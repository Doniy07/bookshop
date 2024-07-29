package org.company.youtube.mapper;

import org.company.youtube.enums.SubscriptionNotificationType;

import java.time.LocalDateTime;

public interface SubscriptionInfoMapper {

    String getSubscriptionId();

    String getChannelId();

    String getChannelName();

    String getChannelPhotoId();

    SubscriptionNotificationType getNotificationType();

    LocalDateTime getCreatedDate();
}
