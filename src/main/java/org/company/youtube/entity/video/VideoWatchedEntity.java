package org.company.youtube.entity.video;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.company.youtube.entity.playlist.PlaylistEntity;
import org.hibernate.annotations.UuidGenerator;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "video_watched")
public class VideoWatchedEntity {
    @Id
    @UuidGenerator
    private String id;

    @Column(name = "playlist_id")
    private String playlistId;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "playlist_id", insertable = false, updatable = false)
    private PlaylistEntity playlist;

    @Column(name = "video_id")
    private String videoId;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "video_id", updatable = false, insertable = false)
    private VideoEntity video;

    @Column(name = "created_date")
    private LocalDateTime createdDate = LocalDateTime.now();
}

