package org.company.youtube.service.comment;

import lombok.RequiredArgsConstructor;
import org.company.youtube.dto.ApiResponse;
import org.company.youtube.dto.profile.ProfileDTO;
import org.company.youtube.dto.record.comment.CommentRequest;
import org.company.youtube.dto.record.comment.CommentResponse;
import org.company.youtube.dto.video.VideoDTO;
import org.company.youtube.entity.comment.CommentEntity;
import org.company.youtube.entity.profile.ProfileEntity;
import org.company.youtube.enums.ProfileRole;
import org.company.youtube.exception.AppBadException;
import org.company.youtube.mapper.CommentInfoMapper;
import org.company.youtube.repository.comment.CommentRepository;
import org.company.youtube.usecase.CommentUseCase;
import org.company.youtube.util.SecurityUtil;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.function.Function;

@Service
@RequiredArgsConstructor
public class CommentService implements CommentUseCase<CommentRequest, CommentResponse> {

    private final CommentRepository commentRepository;

    @Override
    public ApiResponse<CommentResponse> create(CommentRequest commentRequest) {

        if (commentRequest.replyId() != null) {
            get(commentRequest.replyId(), "Reply Comment not found");
        }

        CommentEntity entity = commentRepository.save(mapToEntity().apply(commentRequest));
        CommentResponse response = mapToResponse().apply(entity);
        return ApiResponse.ok(response);

    }

    @Override
    public ApiResponse<CommentResponse> update(String id, CommentRequest.CommentUpdate request) {
        var profile = SecurityUtil.getProfile();
        var entity = get(id, "Comment not found");

        if (profile.getId().equals(entity.getProfileId())) {
            entity.setContent(request.content());
            commentRepository.save(entity);
        }

        CommentResponse response = mapToResponse().apply(entity);
        return ApiResponse.ok(response);
    }

    @Override
    public ApiResponse<String> delete(String id) {
        var profile = SecurityUtil.getProfile();
        var entity = get(id, "Comment not found");
        if (profile.getRole().equals(ProfileRole.ROLE_ADMIN)) {
            entity.setVisible(Boolean.FALSE);
            commentRepository.save(entity);
        }
        if (profile.getId().equals(entity.getProfileId())) {
            entity.setVisible(Boolean.FALSE);
            commentRepository.save(entity);
        }
        return new ApiResponse<>("Comment deleted", 200);
    }

    @Override
    public ApiResponse<Page<CommentResponse>> pagination(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<CommentEntity> pageList = commentRepository.findAll(pageable);
        List<CommentResponse> list = pageList.getContent().stream().map(mapToResponse()).toList();

        return ApiResponse.ok(new PageImpl<>(list, pageList.getPageable(), pageList.getTotalElements()));
    }

    @Override
    public ApiResponse<Page<CommentResponse>> paginationByProfileId(int page, int size, String profileId) {

        Pageable pageable = PageRequest.of(page, size);
        ProfileEntity profile = SecurityUtil.getProfile();
        Page<CommentInfoMapper> pageList = null;
        if (profileId != null && profile.getRole().equals(ProfileRole.ROLE_ADMIN)) {
            pageList = commentRepository.findAllByProfileIdAndVisible(profileId, null, pageable);
        }
        if (profileId == null && profile.getRole().equals(ProfileRole.ROLE_USER)) {
            pageList = commentRepository.findAllByProfileIdAndVisible(profile.getId(), Boolean.TRUE, pageable);

        }
        assert pageList != null;
        List<CommentResponse> list = pageList.getContent().stream().map(mapperMapToResponse()).toList();

        return ApiResponse.ok(new PageImpl<>(list, pageList.getPageable(), pageList.getTotalElements()));
    }

    @Override
    public ApiResponse<Page<CommentResponse>> paginationByVideoId(int page, int size, String videoId)
    {

        Pageable pageable = PageRequest.of(page, size);
        Page<CommentInfoMapper> pageList = commentRepository.findAllByVideoIdAndVisibleTrue(videoId, pageable);
        List<CommentResponse> list = pageList.getContent()
                .stream()
                .map(mapperMapToResponse())
                .toList();

        return ApiResponse.ok(new PageImpl<>(list, pageList.getPageable(), pageList.getTotalElements()));
    }

    @Override
    public ApiResponse<Page<CommentResponse>> getRepliedComment(int page, int size, String commentId) {
        Pageable pageable = PageRequest.of(page, size);
        Page<CommentInfoMapper> pageList = commentRepository.findAllByReplyIdAndVisibleTrue(commentId, pageable);
        List<CommentResponse> list = pageList.getContent()
                .stream()
                .map(mapperMapToResponse())
                .toList();

        return ApiResponse.ok(new PageImpl<>(list, pageList.getPageable(), pageList.getTotalElements()));
    }

    private Function<CommentRequest, CommentEntity> mapToEntity() {
        return request -> CommentEntity.builder()
                .profileId(SecurityUtil.getProfileId())
                .videoId(request.videoId())
                .content(request.content())
                .replyId(request.replyId() != null ? request.replyId() : null)
                .build();
    }

    private Function<CommentEntity, CommentResponse> mapToResponse() {

        return entity -> CommentResponse.builder()
                .id(entity.getId())
                .profileId(SecurityUtil.getProfileId())
                .videoId(entity.getVideoId())
                .content(entity.getContent())
                .replyId(entity.getReplyId() != null ? entity.getReplyId() : null)
                .createdDate(entity.getCreatedDate()).build();
    }

    private Function<CommentInfoMapper, CommentResponse> mapperMapToResponse() {

        return mapper -> CommentResponse.builder().id(mapper.getCommentId()).content(mapper.getContent()).createdDate(mapper.getCreatedDate()).likeCount(mapper.getLikeCount()).dislikeCount(mapper.getDislikeCount()).video(mapper.getVideoId() != null ? VideoDTO.builder().id(mapper.getVideoId()).title(mapper.getVideoTitle()).previewAttachId(mapper.getVideoPreviewAttachId()).build() : null).profile(mapper.getProfileId() != null ? ProfileDTO.builder().id(mapper.getProfileId()).name(mapper.getProfileName()).surname(mapper.getProfileSurname()).photoId(mapper.getProfilePhotoId()).build() : null).build();
    }

    private CommentEntity get(String id, String text) {
        return commentRepository.findByIdAndVisibleTrue(id).orElseThrow(() -> new AppBadException(text));
    }
}
