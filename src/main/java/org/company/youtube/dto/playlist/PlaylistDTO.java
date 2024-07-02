package org.company.youtube.dto.playlist;

import lombok.Getter;
import lombok.Setter;
import org.company.youtube.dto.channel.ChannelDTO;
import org.company.youtube.enums.PlaylistStatus;

@Getter
@Setter
public class PlaylistDTO {

    private String id;
    private String channelId;
    private ChannelDTO channel;
    private String name;
    private String description;
    private String orderNum;
    private PlaylistStatus status;
    private Boolean visible;
    private String createdDate;
}
