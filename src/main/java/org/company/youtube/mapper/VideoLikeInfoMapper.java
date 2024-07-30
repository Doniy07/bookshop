package org.company.youtube.mapper;

public interface VideoLikeInfoMapper {

//        id,video(id,name,channel(id,name),duration)
//        ,preview_attach(id,url)

    String getId();

    String getVideoId();

    String getVideoTitle();

    String getChannelId();

    String getChannelName();

    String getPreviewAttachId();
}
