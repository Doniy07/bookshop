package org.company.youtube.entity.playlist;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.company.youtube.dto.attach.AttachDTO;
import org.company.youtube.entity.attach.AttachEntity;
import org.company.youtube.enums.PlaylistStatus;
import org.hibernate.annotations.UuidGenerator;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "playlist")
public class PlaylistEntity {

    @Id
    @UuidGenerator
    private String id;

    @Column(name = "channel_id")
    private String channelId;
    @ManyToOne
    @JoinColumn(name = "channel_id", updatable = false, insertable = false)
    private AttachEntity channel;

    @Column(name = "description")
    private String description;

    @Column(name = "order_num")
    private String orderNum;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private PlaylistStatus status;

    @Column(name = "visible")
    private Boolean visible = Boolean.TRUE;

    @Column(name = "created_date")
    private LocalDateTime createdDate = LocalDateTime.now();
}
