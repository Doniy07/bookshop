package org.company.youtube.entity.playlist;

import jakarta.persistence.*;
import lombok.*;
import org.company.youtube.entity.channel.ChannelEntity;
import org.company.youtube.enums.PlaylistStatus;
import org.hibernate.annotations.UuidGenerator;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "playlist")
public class PlaylistEntity {

    @Id
    @UuidGenerator
    private String id;

    @Column(name = "channel_id")
    private String channelId;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "channel_id", updatable = false, insertable = false)
    private ChannelEntity channel;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "order_num")
    private Integer orderNum;

    @Column(name = "video_count")
    private Integer videoCount;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private PlaylistStatus status;

    @Builder.Default
    @Column(name = "visible")
    private Boolean visible = Boolean.TRUE;

    @Builder.Default
    @Column(name = "created_date")
    private LocalDateTime createdDate = LocalDateTime.now();

    @OneToMany(mappedBy = "playlist", fetch = FetchType.LAZY)
    private List<PlaylistVideoEntity> videos;

}
