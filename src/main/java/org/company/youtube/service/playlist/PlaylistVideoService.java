package org.company.youtube.service.playlist;

import lombok.RequiredArgsConstructor;
import org.company.youtube.dto.channel.ChannelDTO;
import org.company.youtube.dto.playlist.PlaylistDTO;
import org.company.youtube.dto.playlist.PlaylistVideoDTO;
import org.company.youtube.dto.playlist.PlaylistVideoRequestDTO;
import org.company.youtube.dto.profile.ProfileDTO;
import org.company.youtube.dto.video.VideoDTO;
import org.company.youtube.entity.playlist.PlaylistVideoEntity;
import org.company.youtube.mapper.PlaylistVideoInfoMapper;
import org.company.youtube.mapper.VideoShortInfoMapper;
import org.company.youtube.repository.playlist.PlaylistVideoRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
@RequiredArgsConstructor
public class PlaylistVideoService {

    private final PlaylistVideoRepository playlistVideoRepository;

    public void merge(String videoId, String playlistId) {

        playlistVideoRepository.save(PlaylistVideoEntity.builder()
                .orderNum(playlistVideoRepository.findMaxOrderNumber() + 1)
                .videoId(videoId)
                .playlistId(playlistId)
                .build());
    }

    public PlaylistVideoDTO merge(PlaylistVideoRequestDTO dto) {
        PlaylistVideoEntity entity = playlistVideoRepository.save(PlaylistVideoEntity.builder()
//                .orderNum(playlistVideoRepository.findMaxOrderNumber() + 1)
                .orderNum(dto.getOrderNum())
                .videoId(dto.getVideoId())
                .playlistId(dto.getPlaylistId())
                .build());

        return toDTO(entity);
    }

    public PlaylistVideoDTO toDTO(PlaylistVideoEntity entity) {
        return PlaylistVideoDTO.builder()
                .id(entity.getId())
                .orderNum(entity.getOrderNum())
                .playlistId(entity.getPlaylistId())
                .videoId(entity.getVideoId())
                .createdDate(entity.getCreatedDate())
                .build();
    }

    public void delete(PlaylistVideoRequestDTO dto) { // TODO qayta korib chiqish kerak
        playlistVideoRepository.deleteByPlaylistIdAndVideoId(dto.getPlaylistId(), dto.getVideoId());
    }

    //    PlaylistVideoInfo
//            playlist_id,video(id,preview_attach(id,url),title,duration),
//            channel(id,name),created_date, order_num
    public PageImpl<PlaylistVideoDTO> getVideoListByPlaylistId(String playlistId, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<PlaylistVideoInfoMapper> mapperList = playlistVideoRepository.findByPlaylistId(playlistId, pageable);
        return new PageImpl<>(iteratorInfo(mapperList.getContent()), mapperList.getPageable(), mapperList.getTotalElements());

    }

    public List<PlaylistVideoDTO> iteratorInfo(List<PlaylistVideoInfoMapper> mapperList) {
        List<PlaylistVideoDTO> list = mapperList
                .stream()
                .map(mapper -> PlaylistVideoDTO.builder()
                        .playlistId(mapper.getPlaylistId())
                        .video(VideoDTO.builder()
                                .id(mapper.getVideoId())
                                .previewAttachId(mapper.getVideoPreviewAttachId())
                                .title(mapper.getVideoTitle())
                                .channel(ChannelDTO.builder()
                                        .id(mapper.getChannelId())
                                        .name(mapper.getChannelName())
                                        .build())
                                .build())
                        .createdDate(mapper.getCreatedDate())
                        .orderNum(mapper.getOrderNum())
                        .build()
                )
                .toList();
        return list;
    }

}
