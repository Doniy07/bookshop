package org.company.youtube.service.comment;

import lombok.RequiredArgsConstructor;
import org.company.youtube.dto.ApiResponse;
import org.company.youtube.entity.comment.CommentLikeEntity;
import org.company.youtube.entity.comment.CommentLikeRequest;
import org.company.youtube.entity.comment.CommentLikeResponse;
import org.company.youtube.mapper.CommentLikeInfoMapper;
import org.company.youtube.repository.comment.CommentLikeRepository;
import org.company.youtube.usecase.CommentLikeUseCase;
import org.company.youtube.util.SecurityUtil;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

@Service
@RequiredArgsConstructor
public class CommentLikeService implements CommentLikeUseCase<CommentLikeRequest, CommentLikeResponse> {

    private final CommentLikeRepository commentLikeRepository;


    @Override
    public ApiResponse<String> reaction(CommentLikeRequest request) {
        String profileId = SecurityUtil.getProfileId();
        Optional<CommentLikeEntity> optional = commentLikeRepository.findByCommentIdAndProfileId(request.commentId(), profileId);

        if (optional.isEmpty()) {
            CommentLikeEntity entity = commentLikeRepository.save(mapToEntity().apply(request));
            return new ApiResponse<>("Created like for comment " + request.commentId() + " by id: " + entity.getId(), 200);
        }

        CommentLikeEntity entity = optional.get();
        if (entity.getStatus().equals(request.status())) {
            commentLikeRepository.delete(entity);
            return new ApiResponse<>(" Deleted status: " + request.status() + " by ProfileId: " + entity.getProfileId(), 200);
        }

        entity.setStatus(request.status());
        commentLikeRepository.save(entity);
        return new ApiResponse<>("Updated to : " + request.status() + " by ProfileId: " + entity.getProfileId(), 200);
    }

    @Override
    public ApiResponse<Page<CommentLikeResponse>> likedVideoList(int page, int size, String profileId) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("createdDate"));

        Page<CommentLikeInfoMapper> pageEntity = commentLikeRepository.findAllByProfileId(profileId, pageable);
        List<CommentLikeResponse> list = pageEntity.getContent().stream().map(mapperMapToResponse()).toList();

        return ApiResponse.ok(new PageImpl<>(list, pageEntity.getPageable(), pageEntity.getTotalElements()));
    }

    private Function<CommentLikeRequest, CommentLikeEntity> mapToEntity() {
        return request -> CommentLikeEntity.builder()
                .profileId(SecurityUtil.getProfileId())
                .commentId(request.commentId())
                .status(request.status())
                .build();
    }

    private Function<CommentLikeInfoMapper, CommentLikeResponse> mapperMapToResponse() {

        return entity -> CommentLikeResponse.builder()
                .id(entity.getId())
                .profileId(entity.getProfileId())
                .commentId(entity.getCommentId())
                .status(entity.getStatus())
                .createdDate(entity.getCreatedDate())
                .build();
    }
}
