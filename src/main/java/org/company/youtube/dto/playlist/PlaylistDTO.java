package org.company.youtube.dto.playlist;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.company.youtube.dto.channel.ChannelDTO;
import org.company.youtube.enums.PlaylistStatus;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PlaylistDTO {

    private String id;
    private String channelId;
    private ChannelDTO channel;
    private String name;
    private String description;
    private Integer orderNum;
    private PlaylistStatus status;
    private Boolean visible;
    private String createdDate;
}
