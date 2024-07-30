package org.company.youtube.service.video;

import lombok.RequiredArgsConstructor;
import org.company.youtube.dto.ApiResponse;
import org.company.youtube.dto.channel.ChannelDTO;
import org.company.youtube.dto.video.VideoDTO;
import org.company.youtube.dto.video.VideoLikeRequest;
import org.company.youtube.dto.video.VideoLikeResponse;
import org.company.youtube.entity.video.VideoLikeEntity;
import org.company.youtube.mapper.VideoLikeInfoMapper;
import org.company.youtube.repository.video.VideoLikeRepository;
import org.company.youtube.usecase.VideoLikeUseCase;
import org.company.youtube.util.SecurityUtil;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

@Service
@RequiredArgsConstructor
public class VideoLikeService implements VideoLikeUseCase<VideoLikeRequest, VideoLikeResponse> {

    private final VideoLikeRepository videoLikeRepository;

    @Override
    public ApiResponse<String> reaction(VideoLikeRequest request) {
        String profileId = SecurityUtil.getProfileId();
        Optional<VideoLikeEntity> optional = videoLikeRepository.findByVideoIdAndProfileId(request.videoId(), profileId);

        if (optional.isEmpty()) {
            VideoLikeEntity entity = videoLikeRepository.save(mapToEntity().apply(request));
            return new ApiResponse<>("Created like for comment " + request.videoId() + " by id: " + entity.getId(), 200);
        }

        VideoLikeEntity entity = optional.get();
        if (entity.getStatus().equals(request.status())) {
            videoLikeRepository.delete(entity);
            return new ApiResponse<>(" Deleted status: " + request.status() + " by ProfileId: " + entity.getProfileId(), 200);
        }

        entity.setStatus(request.status());
        videoLikeRepository.save(entity);
        return new ApiResponse<>("Updated to : " + request.status() + " by ProfileId: " + entity.getProfileId(), 200);
    }

    @Override
    public ApiResponse<Page<VideoLikeResponse>> likedVideoList(int page, int size, String profileId) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("createdDate"));

        Page<VideoLikeInfoMapper> pageEntity = videoLikeRepository.findAllByProfileId(profileId, pageable);
        List<VideoLikeResponse> list = pageEntity.getContent().stream().map(mapperMapToResponse()).toList();

        return ApiResponse.ok(new PageImpl<>(list, pageEntity.getPageable(), pageEntity.getTotalElements()));

    }


    private Function<VideoLikeRequest, VideoLikeEntity> mapToEntity() {
        return request -> VideoLikeEntity.builder()
                .profileId(SecurityUtil.getProfileId())
                .videoId(request.videoId())
                .status(request.status())
                .build();
    }

    private Function<VideoLikeInfoMapper, VideoLikeResponse> mapperMapToResponse() {

        return entity -> VideoLikeResponse.builder()
                .id(entity.getId())
                .video(VideoDTO.builder()
                        .id(entity.getVideoId())
                        .title(entity.getVideoTitle())
                        .channel(ChannelDTO.builder()
                                .id(entity.getChannelId())
                                .name(entity.getChannelName())
                                .build())
                        .previewAttachId(entity.getPreviewAttachId())
                        .build())
                .build();
    }

}
