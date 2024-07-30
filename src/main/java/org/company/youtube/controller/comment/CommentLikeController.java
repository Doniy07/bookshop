package org.company.youtube.controller.comment;

import lombok.RequiredArgsConstructor;
import org.company.youtube.dto.ApiResponse;
import org.company.youtube.dto.video.VideoLikeRequest;
import org.company.youtube.entity.comment.CommentLikeRequest;
import org.company.youtube.entity.comment.CommentLikeResponse;
import org.company.youtube.usecase.CommentLikeUseCase;
import org.company.youtube.util.SecurityUtil;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/comment-like")
@RequiredArgsConstructor
public class CommentLikeController {

    private final CommentLikeUseCase<CommentLikeRequest, CommentLikeResponse> commentLikeUseCase;



//    1. Create Comment like
//    2. Remove Comment Like

    @GetMapping("/reaction")
    public ResponseEntity<ApiResponse<String>> reaction(@RequestBody CommentLikeRequest request) {
        return ResponseEntity.ok(commentLikeUseCase.reaction(request));
    }

//    3. User Liked Comment List (order by created_date desc) (USER)

    @GetMapping("/user/liked-video-list")
    public ResponseEntity<ApiResponse<Page<CommentLikeResponse>>> likedVideoList(@RequestParam(defaultValue = "1") int page,
                                                                                 @RequestParam(defaultValue = "5") int size) {
        return ResponseEntity.ok(commentLikeUseCase.likedVideoList(page - 1, size, SecurityUtil.getProfileId()));
    }

//    4. Get User LikedComment List By UserId (ADMIN)

    @GetMapping("/adm/liked-video-list/{userId}")
    public ResponseEntity<ApiResponse<Page<CommentLikeResponse>>> likedVideoListByUserId(@RequestParam(defaultValue = "1") int page,
                                                                                         @RequestParam(defaultValue = "5") int size,
                                                                                         @PathVariable("userId") String userId) {
        return ResponseEntity.ok(commentLikeUseCase.likedVideoList(page - 1, size, userId));
    }
}
