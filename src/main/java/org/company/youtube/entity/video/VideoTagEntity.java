package org.company.youtube.entity.video;

import jakarta.persistence.*;
import lombok.Data;
import org.company.youtube.entity.tag.TagEntity;
import org.hibernate.annotations.UuidGenerator;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "video_tag")
public class VideoTagEntity {
    @Id
    @UuidGenerator
    private String id;

    @Column(name = "tag_id")
    private String tagId;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tag_id", updatable = false, insertable = false)
    private TagEntity tag;

    @Column(name = "video_id")
    private String videoId;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "video_id", updatable = false, insertable = false)
    private VideoEntity video;

    @Column(name = "created_date")
    private LocalDateTime createdDate = LocalDateTime.now();
}
