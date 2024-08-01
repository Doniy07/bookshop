package org.company.youtube.service.video;

import lombok.RequiredArgsConstructor;
import org.company.youtube.dto.channel.ChannelDTO;
import org.company.youtube.dto.playlist.PlaylistDTO;
import org.company.youtube.dto.profile.ProfileDTO;
import org.company.youtube.dto.video.VideoCreateDTO;
import org.company.youtube.dto.video.VideoDTO;
import org.company.youtube.dto.video.VideoUpdateDTO;
import org.company.youtube.entity.video.VideoEntity;
import org.company.youtube.enums.VideoStatus;
import org.company.youtube.exception.AppBadException;
import org.company.youtube.mapper.VideoShortInfoMapper;
import org.company.youtube.repository.video.VideoRepository;
import org.company.youtube.service.playlist.PlaylistVideoService;
import org.company.youtube.service.tag.TagService;
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
public class VideoService {

    private final VideoRepository videoRepository;
    private final TagService tagService;
    private final VideoTagService videoTagService;
    private final PlaylistVideoService playlistVideoService;

    public VideoCreateDTO create(VideoCreateDTO dto) {

        VideoEntity entity = VideoEntity.builder().title(dto.getTitle()).description(dto.getDescription()).previewAttachId(dto.getPreviewAttachId()).channelId(dto.getChannelId()).categoryId(dto.getCategoryId()).videoId(dto.getVideoId()).videoType(dto.getVideoType()).build();


        videoRepository.save(entity);
        tagService.create(dto.getTagNames());

        videoTagService.merge(entity.getId(), tagService.getTagsId(dto.getTagNames()));

        if (dto.getPlaylistId() != null) {
            playlistVideoService.merge(entity.getId(), dto.getPlaylistId());
        }

        return VideoCreateDTO.builder().id(entity.getId()).title(entity.getTitle()).description(entity.getDescription()).previewAttachId(entity.getPreviewAttachId()).channelId(entity.getChannelId()).categoryId(entity.getCategoryId()).videoId(entity.getVideoId()).videoType(entity.getVideoType()).playlistId(dto.getPlaylistId() != null ? dto.getPlaylistId() : null).build();
    }

    /*private VideoDTO toDTO(VideoEntity entity) {
        return VideoDTO.builder()
                .id(entity.getId())
                .previewAttachId(entity.getPreviewAttachId())
                .title(entity.getTitle())
                .description(entity.getDescription())
                .playlistId(entity.getPlaylistId() != null ? entity.getPlaylistId() : null)
                .channelId(entity.getChannelId())
                .categoryId(entity.getCategoryId())
                .videoId(entity.getVideoId())
                .videoType(entity.getVideoType())
                .videoStatus(entity.getVideoStatus())
                .viewCount(entity.getViewCount())
                .sharedCount(entity.getSharedCount())
                .likeCount(entity.getLikeCount())
                .dislikeCount(entity.getDislikeCount())
                .createdDate(entity.getCreatedDate())
                .build();
    }*/

    public VideoUpdateDTO update(String videoId, VideoUpdateDTO dto) {

        VideoEntity entity = getVideo(videoId);

        entity.setTitle(dto.getTitle() != null ? dto.getTitle() : entity.getTitle());
        entity.setDescription(dto.getDescription() != null ? dto.getDescription() : entity.getDescription());
        entity.setCategoryId(dto.getCategoryId() != null ? dto.getCategoryId() : entity.getCategoryId());
        videoRepository.save(entity);
        videoTagService.merge(entity.getId(), dto.getTagIds());

        return VideoUpdateDTO.builder().id(entity.getId())  // to delete
                .title(entity.getTitle()).description(entity.getDescription()).categoryId(entity.getCategoryId()).build();
    }

    public VideoEntity getVideo(String videoId) {
        Optional<VideoEntity> optional = videoRepository.findByIdAndVisibleTrue(videoId);
        if (optional.isEmpty()) {
            throw new AppBadException("Video not found");
        }
        return optional.get();
    }

    public VideoDTO changeStatus(String videoId, VideoStatus status) {
        VideoEntity entity = getVideo(videoId);
        entity.setVideoStatus(entity.getVideoStatus() == null ? status : entity.getVideoStatus());
        videoRepository.save(entity);
        return VideoDTO.builder().id(entity.getId()).videoStatus(entity.getVideoStatus()).build();
    }

    public VideoDTO increaseViewCount(String videoId) {
        VideoEntity entity = getVideo(videoId);
        videoRepository.increaseViewCount(videoId);
        return VideoDTO.builder().id(entity.getId()).viewCount(entity.getViewCount()).build();
    }

    public VideoDTO increaseShareCount(String videoId) {
        VideoEntity entity = getVideo(videoId);
        videoRepository.increaseShareCount(videoId);
        return VideoDTO.builder().id(entity.getId()).sharedCount(entity.getSharedCount()).build();
    }

    public PageImpl<VideoDTO> paginationByCategoryId(String categoryId, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<VideoShortInfoMapper> mapperList = videoRepository.findByCategoryId(categoryId, pageable);
        return new PageImpl<>(iteratorShortInfo(mapperList.getContent(), false), mapperList.getPageable(), mapperList.getTotalElements());
    }

    public PageImpl<VideoDTO> searchByTitle(String title, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<VideoShortInfoMapper> mapperList = videoRepository.findByTitle(title, pageable);
        return new PageImpl<>(iteratorShortInfo(mapperList.getContent(), false), mapperList.getPageable(), mapperList.getTotalElements());
    }

    public PageImpl<VideoDTO> paginationByTagId(String tagId, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<VideoShortInfoMapper> mapperList = videoRepository.findByTagId(tagId, pageable);
        return new PageImpl<>(iteratorShortInfo(mapperList.getContent(), false), mapperList.getPageable(), mapperList.getTotalElements());
    }

    public JSONArray getVideoById(String videoId) {
        List<Object[]> results = videoRepository.findByVideoId(videoId);
        JSONArray videos = new JSONArray();

        for (Object[] row : results) {
            JSONObject video = new JSONObject();

            video.put("id", row[0]);
            video.put("title", row[1]);
            video.put("preview_attach_id", row[2]);
            video.put("video_id", row[3]);
            video.put("category_id", row[4]);
            video.put("category_name", row[5]);
            video.put("tag_list", new JSONArray(row[6].toString()));
            video.put("published_date", row[7]);
            video.put("channel_id", row[8]);
            video.put("channel_name", row[9]);
            video.put("channel_photo_id", row[10]);
            video.put("view_count", row[11]);
            video.put("shared_count", row[12]); // TODO: add isUserLiked, IsUserDisliked when created video_like table

            videos.put(video);
        }
        return videos;

    }

    private VideoDTO shortInfo(VideoShortInfoMapper mapper, boolean isAdmin) {

        return VideoDTO.builder().id(mapper.getVideoId()).title(mapper.getVideoTitle()).previewAttachId(mapper.getVideoPreviewAttachId()).publishedDate(mapper.getVideoPublishedDate()).channel(mapper.getChannelId() == null ? null : ChannelDTO.builder().id(mapper.getChannelId()).name(mapper.getChannelName()).photoId(mapper.getChannelPhotoId()).build()).profile(!isAdmin ? null : ProfileDTO.builder().id(mapper.getProfileId()).name(mapper.getProfileName()).surname(mapper.getProfileSurname()).build()).playlist(!isAdmin ? null : PlaylistDTO.builder().id(mapper.getPlaylistId()).name(mapper.getPlaylistName()).build()).viewCount(mapper.getVideoViewCount()).build();
    }

    private List<VideoDTO> iteratorShortInfo(List<VideoShortInfoMapper> mapperList, boolean isAdmin) {
        return mapperList.stream().map(mapper -> shortInfo(mapper, isAdmin)).toList();
    }

    public PageImpl<VideoDTO> videoList(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<VideoShortInfoMapper> mapperList = videoRepository.findAllBy(pageable);
        return new PageImpl<>(iteratorShortInfo(mapperList.getContent(), true), mapperList.getPageable(), mapperList.getTotalElements());
    }

    public PageImpl<VideoDTO> paginationByChannelId(String channelId, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<VideoShortInfoMapper> mapperList = videoRepository.findByChannelId(channelId, pageable);
        return new PageImpl<>(iteratorShortInfo(mapperList.getContent(), false), mapperList.getPageable(), mapperList.getTotalElements());
    }
}
