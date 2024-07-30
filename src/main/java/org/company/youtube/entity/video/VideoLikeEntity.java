package org.company.youtube.entity.video;

import jakarta.persistence.*;
import lombok.*;
import org.company.youtube.entity.profile.ProfileEntity;
import org.company.youtube.enums.EmotionStatus;
import org.hibernate.annotations.UuidGenerator;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "video_like_entity")
public class VideoLikeEntity {
    @Id
    @UuidGenerator
    private String id;

    @Column(name = "profile_id")
    private String profileId;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "profile_id", insertable = false, updatable = false)
    private ProfileEntity profile;

    @Column(name = "video_id")
    private String videoId;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "video_id", insertable = false, updatable = false)
    private VideoEntity video;

    @Enumerated(EnumType.STRING)
    @Column(name = "type")
    private EmotionStatus status;

    @Builder.Default
    @Column(name = "created_date")
    private LocalDateTime createdDate = LocalDateTime.now();
}