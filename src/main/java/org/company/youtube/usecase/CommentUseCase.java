package org.company.youtube.usecase;

import org.company.youtube.dto.ApiResponse;
import org.company.youtube.dto.record.comment.CommentRequest;
import org.springframework.data.domain.Page;

import java.util.List;

public interface CommentUseCase<REQUEST, RESPONSE> {

    ApiResponse<RESPONSE> create(REQUEST request);

    ApiResponse<RESPONSE> update(String id, CommentRequest.CommentUpdate request);

    ApiResponse<String> delete(String id);

    ApiResponse<Page<RESPONSE>> pagination(int page, int size);

    ApiResponse<Page<RESPONSE>> paginationByProfileId(int page, int size, String profileId);

    ApiResponse<Page<RESPONSE>> paginationByVideoId(int page, int size, String videoId);

    ApiResponse<Page<RESPONSE>> getRepliedComment(int page, int size, String commentId);
}
