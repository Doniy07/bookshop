package org.company.youtube.dto.playlist;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.company.youtube.dto.video.VideoDTO;
import org.company.youtube.entity.playlist.PlaylistVideoEntity;

import java.time.LocalDateTime;

/**
 * DTO for {@link PlaylistVideoEntity}
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PlaylistVideoDTO {

    private String id;
    private Long orderNum;
    private String playlistId;
    private PlaylistDTO playlist;
    private String videoId;
    private VideoDTO video;
    private LocalDateTime createdDate;

}