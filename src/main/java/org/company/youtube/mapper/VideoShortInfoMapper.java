package org.company.youtube.mapper;

import java.time.LocalDateTime;

public interface VideoShortInfoMapper {

    String getVideoId();
    String getVideoTitle();
    String getVideoPreviewAttachId();
    LocalDateTime getVideoPublishedDate();
    String getChannelId();
    String getChannelName();
    String getChannelPhotoId();
    Long getVideoViewCount();
    String getProfileId();
    String getProfileName();
    String getProfileSurname();
    String getPlaylistId();
    String getPlaylistName();
}
