package org.company.youtube.dto.channel;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.company.youtube.dto.attach.AttachDTO;
import org.company.youtube.dto.profile.ProfileDTO;
import org.company.youtube.enums.ChannelStatus;

import java.time.LocalDateTime;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ChannelDTO {
    private String id;
    private String name;
    private String photoId;
    private AttachDTO photo;
    private String description;
    private ChannelStatus status;
    private String bannerId;
    private AttachDTO banner;
    private String profileId;
    private ProfileDTO profile;
    private LocalDateTime createdDate;
}
