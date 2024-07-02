package org.company.youtube.repository.playlist;


import org.company.youtube.entity.playlist.PlaylistEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface PlaylistRepository extends CrudRepository<PlaylistEntity, String>,
        PagingAndSortingRepository<PlaylistEntity, String> {

    @Query("select pl.id, pl.name, pl.description, pl.status, pl.orderNum, " +
            "c.id as channelId, c.name,c.photoId, " +
            "pr.id, pr.name, pr.surname, pr.photoId" +
            " from PlaylistEntity pl " +
            "inner join ChannelEntity c on pl.channelId = c.id" +
            " inner join ProfileEntity pr on c.profileId = pr.id" +
            " where pr.id = ?1")
    Page<PlaylistEntity> findAllByUserId(String userId, Pageable pageable);
}
