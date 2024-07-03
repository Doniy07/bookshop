package org.company.youtube.mapper;

import org.company.youtube.enums.PlaylistStatus;

public interface PlaylistInfoMapper {

//      PlaylistInfoMapper
//            id,name,description,status(private,public),order_num,
//            channel(id,name,photo(id,url), profile(id,name,surname,photo(id,url)))
    String getPlaylistId();
    String getPlaylistName();
    String getDescription();
    PlaylistStatus getStatus();
    Integer getOrderNum();
    String getChannelId();
    String getChannelName();
    String getChannelPhotoId();
    String getProfileId();
    String getProfileName();
    String getProfileSurname();
    String getProfilePhotoId();
}
