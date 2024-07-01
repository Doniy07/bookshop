package org.company.youtube.service.playlist;

import lombok.RequiredArgsConstructor;
import org.company.youtube.dto.playlist.PlaylistCreateDTO;
import org.company.youtube.dto.playlist.PlaylistDTO;
import org.company.youtube.dto.playlist.PlaylistUpdateDTO;
import org.company.youtube.entity.playlist.PlaylistEntity;
import org.company.youtube.enums.PlaylistStatus;
import org.company.youtube.exception.AppBadException;
import org.company.youtube.repository.playlist.PlaylistRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PlaylistService {

    private final PlaylistRepository playlistRepository;


    public PlaylistDTO create(PlaylistCreateDTO dto) {

        PlaylistEntity entity = new PlaylistEntity();
        entity.setChannelId(dto.getChannelId());
        entity.setDescription(dto.getDescription());
        entity.setOrderNum(dto.getOrderNum());
        entity.setStatus(dto.getStatus());
        entity = playlistRepository.save(entity);
        return toDTO(entity);
    }

    private PlaylistDTO toDTO(PlaylistEntity entity) {
        PlaylistDTO dto = new PlaylistDTO();
        dto.setChannelId(entity.getChannelId());
        dto.setDescription(entity.getDescription());
        dto.setOrderNum(entity.getOrderNum());
        dto.setStatus(entity.getStatus());
        return dto;
    }

    public PlaylistEntity getPlaylistById(String playlistId) {
        Optional<PlaylistEntity> optional = playlistRepository.findById(playlistId);
        if (optional.isEmpty()) {
            throw new AppBadException("Channel not found");
        }
        return optional.get();
    }

    public PlaylistDTO update(String playlistId, PlaylistUpdateDTO dto) {

        PlaylistEntity entity = getPlaylistById(playlistId);
        entity.setDescription(dto.getDescription());
        entity.setOrderNum(dto.getOrderNum());
        entity = playlistRepository.save(entity);
        return toDTO(entity);

    }

    public PlaylistDTO changeStatus(String playlistId, PlaylistStatus status) {

        PlaylistEntity entity = getPlaylistById(playlistId);
        if (entity.getStatus().equals(status)) {
            return toDTO(entity);
        }
        entity.setStatus(status);
        entity = playlistRepository.save(entity);
        return toDTO(entity);

    }
}
