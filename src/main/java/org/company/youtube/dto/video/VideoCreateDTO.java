package org.company.youtube.dto.video;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.company.youtube.enums.VideoType;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class VideoCreateDTO {

    private String id;
    private String title;
    private String description;
    private String previewAttachId;
    private String playlistId;
    private String channelId;
    private Integer categoryId;
    private String videoId;
    private VideoType videoType;
    private List<String> tagNames;

}
