package org.company.youtube.usecase;

import org.company.youtube.dto.ApiResponse;
import org.springframework.data.domain.Page;

public interface CommentLikeUseCase<REQUEST, RESPONSE> {
    ApiResponse<String> reaction(REQUEST request);

    ApiResponse<Page<RESPONSE>> likedVideoList(int page, int size, String profileId);
}
