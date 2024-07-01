package org.company.youtube.dto.channel;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChannelUpdateDTO {

    private String name;
    private String description;
    private String bannerId;
    private String photoId;

}
