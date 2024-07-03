package org.company.youtube.dto.playlist;

import lombok.Data;

@Data
public class PlaylistVideoRequestDTO {

    private Long orderNum;

    private String playlistId;

    private String videoId;
}
