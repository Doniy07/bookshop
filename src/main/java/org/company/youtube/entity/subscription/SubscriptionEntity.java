package org.company.youtube.entity.subscription;

import jakarta.persistence.*;
import lombok.*;
import org.company.youtube.entity.channel.ChannelEntity;
import org.company.youtube.entity.profile.ProfileEntity;
import org.company.youtube.enums.SubscriptionNotificationType;
import org.company.youtube.enums.SubscriptionStatus;
import org.hibernate.annotations.UuidGenerator;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "subscription_entity")
public class SubscriptionEntity {
    //    id,profile_id,channel_id,created_date,
//    unsubscribe_date, status (active,block),
//    notification_type(All,Personalized,Non)
    @Id
    @UuidGenerator
    private String id;

    @Column(name = "profile_id")
    private String profileId;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "profile_id", insertable = false, updatable = false)
    private ProfileEntity profile;

    @Column(name = "channel_id")
    private String channelId;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "channel_id", insertable = false, updatable = false)
    private ChannelEntity channel;

    @Builder.Default
    @Column(name = "created_date")
    private LocalDateTime createdDate = LocalDateTime.now();

    @Column(name = "unsubscribe_date")
    private LocalDateTime unsubscribeDate;

    @Builder.Default
    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private SubscriptionStatus status = SubscriptionStatus.ACTIVE;

    @Column(name = "type")
    @Enumerated(EnumType.STRING)
    private SubscriptionNotificationType type;
}
