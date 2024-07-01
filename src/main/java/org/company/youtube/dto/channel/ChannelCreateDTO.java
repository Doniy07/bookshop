package org.company.youtube.dto.channel;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChannelCreateDTO {

    private String name;
    private String photoId;
    private String description;
    private String bannerId;

}
