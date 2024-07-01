package org.company.youtube.dto.tag;


import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class TagDTO {

    private Integer id;
    private String name;
    private Boolean visible;
    private LocalDateTime createdDate;
}
