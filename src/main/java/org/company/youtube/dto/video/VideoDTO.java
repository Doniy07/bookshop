package org.company.youtube.dto.video;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.company.youtube.dto.attach.AttachDTO;
import org.company.youtube.dto.channel.ChannelDTO;
import org.company.youtube.dto.playlist.PlaylistDTO;
import org.company.youtube.dto.profile.ProfileDTO;
import org.company.youtube.entity.category.CategoryEntity;
import org.company.youtube.enums.VideoStatus;
import org.company.youtube.enums.VideoType;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class VideoDTO {

    private String id;

    private String previewAttachId;

    private AttachDTO previewAttach;

    private String title;

    private String description;

    private String playlistId;

    private PlaylistDTO playlist;

    private String channelId;

    private ChannelDTO channel;

    private Integer categoryId;

    private CategoryEntity category;

    private String videoId;

    private AttachDTO video;

    private LocalDateTime createdDate;

    private LocalDateTime publishedDate;

    private VideoStatus videoStatus;

    private VideoType videoType;

    private Boolean visible;

    private Long viewCount;

    private Long sharedCount;

    private Long likeCount;

    private Long dislikeCount;

    private ProfileDTO profile;

}
