package org.company.youtube.repository.playlist;


import org.company.youtube.entity.playlist.PlaylistEntity;
import org.springframework.data.repository.CrudRepository;

public interface PlaylistRepository extends CrudRepository<PlaylistEntity, String> {
}
