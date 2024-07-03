package org.company.youtube.mapper;

import java.time.LocalDateTime;

public interface PlaylistVideoInfoMapper {

//     PlaylistVideoInfo
//            playlist_id,video(id,preview_attach(id,url),title,duration),
//            channel(id,name),created_date, order_num

    String getPlaylistId();

    String getVideoId();

    String getVideoTitle();

    String getVideoPreviewAttachId();

    String getChannelId();

    String getChannelName();

    LocalDateTime getCreatedDate();

    Long getOrderNum();
}
