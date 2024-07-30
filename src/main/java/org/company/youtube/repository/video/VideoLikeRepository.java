package org.company.youtube.repository.video;

import org.company.youtube.entity.video.VideoLikeEntity;
import org.company.youtube.mapper.VideoLikeInfoMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.Optional;

public interface VideoLikeRepository extends JpaRepository<VideoLikeEntity, String> ,
        PagingAndSortingRepository<VideoLikeEntity, String> {
    Optional<VideoLikeEntity> findByVideoIdAndProfileId(String videoId, String profileId);

    @Query(" select " +
            " vl.id as id," +
            " vl.videoId as videoId, " +
            " v.title as videoTitle, " +
            " v.previewAttachId as previewAttachId, " +
            " v.channelId as channelId, " +
            " c.name as channelName " +
            " from VideoLikeEntity vl " +
            " inner join vl.video as v " +
            " inner join v.channel as c " +
            " where vl.status = 'LIKE' and vl.profileId = ?1 ")
    Page<VideoLikeInfoMapper> findAllByProfileId(String profileId, Pageable pageable);
//    VideoLikeInfo
//        id,video(id,name,channel(id,name),duration),preview_attach(id,url)
}
