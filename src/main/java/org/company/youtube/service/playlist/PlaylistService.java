package org.company.youtube.service.playlist;

import lombok.RequiredArgsConstructor;
import org.company.youtube.dto.playlist.PlaylistCreateDTO;
import org.company.youtube.dto.playlist.PlaylistDTO;
import org.company.youtube.dto.playlist.PlaylistUpdateDTO;
import org.company.youtube.entity.playlist.PlaylistEntity;
import org.company.youtube.enums.PlaylistStatus;
import org.company.youtube.exception.AppBadException;
import org.company.youtube.mapper.PlaylistInfoMapper;
import org.company.youtube.repository.playlist.PlaylistRepository;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PlaylistService {

    private final PlaylistRepository playlistRepository;


    public PlaylistDTO create(PlaylistCreateDTO dto) {

        PlaylistEntity entity = new PlaylistEntity();
        entity.setChannelId(dto.getChannelId());
        entity.setName(dto.getName());
        entity.setDescription(dto.getDescription());
        entity.setOrderNum(dto.getOrderNum());
        entity.setStatus(dto.getStatus());
        entity = playlistRepository.save(entity);
        return toDTO(entity);
    }

    private PlaylistDTO toDTO(PlaylistEntity entity) {
        PlaylistDTO dto = new PlaylistDTO();
        dto.setId(entity.getId());
        dto.setChannelId(entity.getChannelId());
        dto.setName(entity.getName());
        dto.setDescription(entity.getDescription());
        dto.setOrderNum(entity.getOrderNum());
        dto.setStatus(entity.getStatus());
        return dto;
    }

    private PlaylistEntity getPlaylistById(String playlistId) {
        Optional<PlaylistEntity> optional = playlistRepository.findById(playlistId);
        if (optional.isEmpty()) {
            throw new AppBadException("Channel not found");
        }
        return optional.get();
    }

    public PlaylistDTO update(String playlistId, PlaylistUpdateDTO dto) {

        PlaylistEntity entity = getPlaylistById(playlistId);
        entity.setName(dto.getName());
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

    public PlaylistDTO delete(String playlistId) {

        PlaylistEntity entity = getPlaylistById(playlistId);
        entity.setVisible(Boolean.FALSE);
        playlistRepository.save(entity);
        return toDTO(entity);

    }

    public PageImpl<PlaylistInfoMapper> pagination(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<PlaylistInfoMapper> list = playlistRepository.findAllPagination(pageable);
        return new PageImpl<>(list.getContent(), list.getPageable(), list.getTotalElements());
    }

    public JSONArray getPlaylistsByUserId(String userId) {
        List<Object[]> results = playlistRepository.findPlaylistsByUserId(userId);
        return getPlayListShortInfo(results);
    }

    public PageImpl<PlaylistInfoMapper> getUserPlaylist(int page, int size, String userId) {
        Pageable pageable = PageRequest.of(page, size);
        Page<PlaylistInfoMapper> mapperList = playlistRepository.getUserPlaylist(userId, pageable);
        return new PageImpl<>(mapperList.getContent(), pageable, mapperList.getTotalElements());
    }

    public JSONArray getChannelPlaylist(String channelId) {
        List<Object[]> results = playlistRepository.getChannelPlaylist(channelId);
        return getPlayListShortInfo(results);
    }

    public JSONArray getPlayListShortInfo(List<Object[]> results) {
        JSONArray playlists = new JSONArray();

        for (Object[] row : results) {
            JSONObject playlist = new JSONObject();
            playlist.put("playlist_id", row[0]);
            playlist.put("playlist_name", row[1]);
            playlist.put("created_date", row[2]);
            playlist.put("channel_id", row[3]);
            playlist.put("channel_name", row[4]);
            playlist.put("video_count", row[5]);
            playlist.put("video_list", new JSONArray(row[6].toString()));

            playlists.put(playlist);
        }
        return playlists;
    }

    public JSONArray getPlaylist(String playlistId) {

        List<Object[]> results = playlistRepository.getPlaylistById(playlistId);

        JSONArray playlists = new JSONArray();

        for (Object[] row : results) {
            JSONObject playlist = new JSONObject();
            playlist.put("playlist_id", row[0]);
            playlist.put("playlist_name", row[1]);
            playlist.put("video_count", row[2]);
            playlist.put("total_view_count", row[3]);
            playlist.put("last_update_date", row[4]);

            playlists.put(playlist);
        }
        return playlists;
    }
}
