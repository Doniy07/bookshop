package org.company.youtube.entity.playlist;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.company.youtube.entity.video.VideoEntity;
import org.hibernate.annotations.UuidGenerator;

import java.time.LocalDateTime;

/**
 * entity for {@link org.company.youtube.dto.playlist.PlaylistVideoDTO}
 */

@Data
@Entity
@Table(name = "playlist_video")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PlaylistVideoEntity {

    @Id
    @UuidGenerator
    private String id;

    @Column(name = "order_num")
    private Long orderNum;

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

    @Builder.Default
    @Column(name = "created_date")
    private LocalDateTime createdDate = LocalDateTime.now();
}
