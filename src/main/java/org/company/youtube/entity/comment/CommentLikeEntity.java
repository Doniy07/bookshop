package org.company.youtube.entity.comment;

import jakarta.persistence.*;
import lombok.*;
import org.company.youtube.entity.profile.ProfileEntity;
import org.company.youtube.entity.video.VideoEntity;
import org.company.youtube.enums.EmotionStatus;
import org.hibernate.annotations.UuidGenerator;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "comment_like_entity")
public class CommentLikeEntity {

    @Id
    @UuidGenerator
    private String id;

    @Column(name = "profile_id")
    private String profileId;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "profile_id", insertable = false, updatable = false)
    private ProfileEntity profile;

    @Column(name = "comment_id")
    private String commentId;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "comment_id", insertable = false, updatable = false)
    private CommentEntity comment;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private EmotionStatus status;

    @Builder.Default
    @Column(name = "created_date")
    private LocalDateTime createdDate = LocalDateTime.now();

}