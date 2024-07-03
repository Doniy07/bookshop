package org.company.youtube.dto.playlist;

import lombok.Getter;
import lombok.Setter;
import org.company.youtube.enums.PlaylistStatus;

@Getter
@Setter
public class PlaylistCreateDTO {

    private String channelId;
    private String name;
    private String description;
    private Integer orderNum;
    private PlaylistStatus status;

}
