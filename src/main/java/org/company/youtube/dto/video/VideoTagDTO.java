package org.company.youtube.dto.video;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.company.youtube.dto.tag.TagDTO;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class VideoTagDTO {

    private String id;
    private String tagId;
    private TagDTO tag;
    private String videoId;
    private VideoDTO video;
    private LocalDateTime createdDate;

}
