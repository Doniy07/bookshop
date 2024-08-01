package org.company.youtube.mapper;

import org.company.youtube.enums.ReportType;

public interface RepostInfoMapper {

//    RepostInfo
//        id,profile(id,name,surname,photo(id,url)),content,
//            entity_id(channel/video)),type(channel,video)

    String getId();

    String getProfileId();

    String getProfileName();

    String getProfileSurname();

    String getProfilePhotoId();

    String getContent();

    String getEntityId();

    ReportType getType();
}
