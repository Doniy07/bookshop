package org.company.youtube.dto.playlist;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PlaylistUpdateDTO {

    private String name;
    private String description;
    private Integer orderNum;

}
