package org.company.youtube.repository.playlist;


import org.company.youtube.entity.playlist.PlaylistEntity;
import org.company.youtube.mapper.PlaylistInfoMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PlaylistRepository extends JpaRepository<PlaylistEntity, String>,
        PagingAndSortingRepository<PlaylistEntity, String> {

//            PlayListInfo
//            id,name,description,status(private,public),order_num,
//            channel(id,name,photo(id,url), profile(id,name,surname,photo(id,url)))
//    @Query("select pl.id, pl.name, pl.description, pl.status, pl.orderNum, " +
//            "c.id as channelId, c.name,c.photoId, " +
//            "pr.id, pr.name, pr.surname, pr.photoId" +
//            " from PlaylistEntity pl " +
//            "inner join ChannelEntity c on pl.channelId = c.id" +
//            " inner join ProfileEntity pr on c.profileId = pr.id" +
//            " where pr.id = ?1")
//    Page<PlaylistInfoMapper> findAllByUserId(String userId, Pageable pageable);

    //            PlayListInfo
//            id,name,description,status(private,public),order_num,
//            channel(id,name,photo(id,url), profile(id,name,surname,photo(id,url)))
    @Query( " SELECT " +
            " p.id AS playlistId, " +
            " p.name AS playlistName, " +
            " p.description, " +
            " p.status, " +
            " p.orderNum, " +
            " ch.id AS channelId, " +
            " ch.name AS chanelName, " +
            " ch.photoId AS chanelPhotoId, " +
            " pr.id AS profileId, " +
            " pr.name AS profileName, " +
            " pr.surname AS profileSurname, " +
            " pr.photoId AS profilePhotoId " +
            " FROM PlaylistEntity p " +
            " INNER JOIN p.channel AS ch " +
            " INNER JOIN ProfileEntity pr ON ch.profileId = pr.id " +
            " GROUP BY p.id, p.name, ch.id, ch.name " +
            " ORDER BY p.orderNum DESC ")
    Page<PlaylistInfoMapper> findAllPagination(Pageable pageable);


//    PlayListShortInfo
//            id, name,created_date,channel(id,name),video_count,video_list[{id,name,duration}]
    @Query( " SELECT " +
            " p.id AS playlistId, " +
            " p.name AS playlistName, " +
            " p.createdDate, " +
            " ch.id AS chanelId, " +
            " ch.name AS chanelName, " +
            " COUNT(pv.videoId) AS videoCount, " +
            " json_agg(json_build_object( " +
            "        'id', v.id, " +
            "        'title', v.title " +
            "    )) AS videos " +
            " FROM PlaylistEntity p " +
            " INNER JOIN p.channel AS ch " +
            " INNER JOIN PlaylistVideoEntity pv ON p.id = pv.playlistId " +
            " INNER JOIN VideoEntity v ON pv.videoId = v.id " +
            " WHERE ch.profileId = ?1 " +
            " GROUP BY p.id, p.name, ch.id, ch.name ")
    List<Object[]> findPlaylistsByUserId(@Param("userId") String userId);

    @Query( " SELECT " +
            " p.id AS playlistId, " +
            " p.name AS playlistName, " +
            " p.description, " +
            " p.status, " +
            " p.orderNum, " +
            " ch.id AS channelId, " +
            " ch.name AS chanelName, " +
            " ch.photoId AS chanelPhotoId, " +
            " pr.id AS profileId, " +
            " pr.name AS profileName, " +
            " pr.surname AS profileSurname, " +
            " pr.photoId AS profilePhotoId " +
            " FROM PlaylistEntity p " +
            " INNER JOIN p.channel AS ch " +
            " INNER JOIN ProfileEntity pr ON ch.profileId = pr.id " +
            " WHERE ch.profileId = ?1 " +
            " GROUP BY p.id, p.name, ch.id, ch.name " +
            " ORDER BY p.orderNum DESC ")
    Page<PlaylistInfoMapper> getUserPlaylist(@Param("userId") String userId, Pageable pageable);

    @Query( " SELECT " +
            " p.id AS playlistId, " +
            " p.name AS playlistName, " +
            " p.description, " +
            " p.status, " +
            " p.orderNum, " +
            " ch.id AS channelId, " +
            " ch.name AS chanelName, " +
            " ch.photoId AS chanelPhotoId, " +
            " pr.id AS profileId, " +
            " pr.name AS profileName, " +
            " pr.surname AS profileSurname, " +
            " pr.photoId AS profilePhotoId " +
            " FROM PlaylistEntity p " +
            " INNER JOIN p.channel AS ch " +
            " INNER JOIN ProfileEntity pr ON ch.profileId = pr.id " +
            " WHERE channelId= ?1 " +
            " GROUP BY p.id, p.name, ch.id, ch.name ")
    List<Object[]> getChannelPlaylist(@Param("channelId") String channelId);

    @Query( " SELECT " +
            " p.id AS playlistId, " +
            " p.name AS playlistName, " +
            " COUNT(pv.videoId) AS videoCount, " +
            " SUM(v.viewCount) AS totalViewCount, " +
            " MAX (pv.createdDate) AS lastUpdateDate  " +
            " FROM PlaylistEntity p " +
            " INNER JOIN p.channel AS ch " +
            " INNER JOIN p.videos pv " +
            " INNER JOIN VideoEntity v ON pv.videoId = v.id " +
            " WHERE playlistId = ?1 " +
            " GROUP BY p.id, p.name ")
    List<Object[]> getPlaylistById(@Param("playlistId") String playlistId);
}

