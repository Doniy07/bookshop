package org.company.youtube.entity.video;

import jakarta.persistence.*;
import lombok.*;
import org.company.youtube.entity.attach.AttachEntity;
import org.company.youtube.entity.category.CategoryEntity;
import org.company.youtube.entity.channel.ChannelEntity;
import org.company.youtube.entity.comment.CommentEntity;
import org.company.youtube.enums.VideoStatus;
import org.company.youtube.enums.VideoType;
import org.hibernate.annotations.UuidGenerator;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "video")
public class VideoEntity {

    @Id
    @UuidGenerator
    private String id;

    @Column(name = "preview_attach_id")
    private String previewAttachId;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "preview_attach_id", insertable = false, updatable = false)
    private AttachEntity previewAttachPhoto;

    @Column(name = "title")
    private String title;

    @Column(name = "description")
    private String description;

    @Column(name = "channel_id")
    private String channelId;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "channel_id", updatable = false, insertable = false)
    private ChannelEntity channel;

    @Column(name = "category_id")
    private Integer categoryId;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", updatable = false, insertable = false)
    private CategoryEntity category;

    @Column(name = "attach_id")
    private String videoId;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "attach_id", insertable = false, updatable = false)
    private AttachEntity video;

    @Builder.Default
    @Column(name = "created_date")
    private LocalDateTime createdDate = LocalDateTime.now();

    @Column(name = "published_date")
    private LocalDateTime publishedDate;

    @Column(name = "video_status")
    @Enumerated(EnumType.STRING)
    private VideoStatus videoStatus;

    @Column(name = "video_type")
    @Enumerated(EnumType.STRING)
    private VideoType videoType;

    @Builder.Default
    @Column(name = "visible")
    private Boolean visible = Boolean.TRUE;

    @Column(name = "view_count")
    private Long viewCount;

    @Column(name = "shared_count")
    private Long sharedCount;

    @Column(name = "like_count")
    private Long likeCount;

    @Column(name = "dislike_count")
    private Long dislikeCount;

    @OneToMany(mappedBy = "video", fetch = FetchType.LAZY)
    private List<VideoTagEntity> tags;

    @OneToMany(mappedBy = "video", fetch = FetchType.LAZY)
    private List<CommentEntity> comments;
}
