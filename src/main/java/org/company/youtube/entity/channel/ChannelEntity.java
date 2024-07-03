package org.company.youtube.entity.channel;

import jakarta.persistence.*;
import lombok.Data;
import org.company.youtube.entity.attach.AttachEntity;
import org.company.youtube.entity.profile.ProfileEntity;
import org.company.youtube.entity.video.VideoEntity;
import org.company.youtube.entity.video.VideoTagEntity;
import org.company.youtube.enums.ChannelStatus;
import org.hibernate.annotations.UuidGenerator;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Entity
@Table(name = "channel")
public class ChannelEntity {

    @Id
    @UuidGenerator
    private String id;

    @Column(name = "name")
    private String name;

    @Column(name = "photo_id")
    private String photoId;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "photo_id", insertable = false, updatable = false)
    private AttachEntity photo;

    @Column(name = "description")
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private ChannelStatus status = ChannelStatus.ACTIVE;

    @Column(name = "banner_id")
    private String bannerId;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "banner_id", insertable = false, updatable = false)
    private AttachEntity banner;

    @Column(name = "profile_id")
    private String profileId;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "profile_id", insertable = false, updatable = false)
    private ProfileEntity profile;

    @Column(name = "created_date")
    private LocalDateTime createdDate = LocalDateTime.now();

    @OneToMany(mappedBy = "channel", fetch = FetchType.LAZY)
    private List<VideoEntity> videos;

}
