package org.company.youtube.repository.playlist;

import jakarta.transaction.Transactional;
import org.company.youtube.entity.playlist.PlaylistVideoEntity;
import org.company.youtube.mapper.PlaylistVideoInfoMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface PlaylistVideoRepository extends JpaRepository<PlaylistVideoEntity, String> {

    @Query(value = "SELECT MAX(order_number) FROM playlist_video", nativeQuery = true)
    Long findMaxOrderNumber();

    PlaylistVideoEntity findByPlaylistIdAndVideoId(String playlistId, String videoId);


    //    PlaylistVideoInfo
//            playlist_id,video(id,preview_attach(id,url),title,duration),
//            channel(id,name),created_date, order_num
    @Query("  SELECT " +
            " pv.playlistId AS playlistId, " +
            " v.videoId AS videoId, " +
            " v.previewAttachId AS videoPreviewAttachId, " +
            " v.title AS videoTitle, " +
            " c.id AS channelId," +
            " c.name AS channelName, " +
            " pv.createdDate AS createdDate, " +
            " pv.orderNum AS orderNum " +
            " FROM PlaylistVideoEntity pv " +
            " INNER JOIN pv.video v" +
            " INNER JOIN v.channel c" +
            " WHERE playlistId = ?1")
    Page<PlaylistVideoInfoMapper> findByPlaylistId(String playlistId, Pageable pageable);

    @Transactional
    @Modifying
    void deleteByPlaylistIdAndVideoId(String playlistId, String videoId);
}
