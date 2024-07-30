package org.company.youtube.controller.video;

import lombok.RequiredArgsConstructor;
import org.company.youtube.dto.ApiResponse;
import org.company.youtube.dto.video.VideoLikeRequest;
import org.company.youtube.dto.video.VideoLikeResponse;
import org.company.youtube.usecase.VideoLikeUseCase;
import org.company.youtube.util.SecurityUtil;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/video-like")
@RequiredArgsConstructor
public class VideoLikeController {

    private final VideoLikeUseCase<VideoLikeRequest, VideoLikeResponse> videoLikeUseCase;

//     1. Create Video like
//     2. Remove Video Like

    @GetMapping("/reaction")
    public ResponseEntity<ApiResponse<String>> reaction(@RequestBody VideoLikeRequest request) {
        return ResponseEntity.ok(videoLikeUseCase.reaction(request));
    }

//     3. User Liked Video List (order by created_date desc) (USER)

    @GetMapping("/user/liked-video-list")
    public ResponseEntity<ApiResponse<Page<VideoLikeResponse>>> likedVideoList(@RequestParam(defaultValue = "1") int page,
                                                                               @RequestParam(defaultValue = "5") int size) {
        return ResponseEntity.ok(videoLikeUseCase.likedVideoList(page - 1, size, SecurityUtil.getProfileId()));
    }

//    4. Get User LikedVideo List By UserId (ADMIN)

    @GetMapping("/adm/liked-video-list/{userId}")
    public ResponseEntity<ApiResponse<Page<VideoLikeResponse>>> likedVideoListByUserId(@RequestParam(defaultValue = "1") int page,
                                                                                       @RequestParam(defaultValue = "5") int size,
                                                                                       @PathVariable("userId") String userId) {
        return ResponseEntity.ok(videoLikeUseCase.likedVideoList(page - 1, size, userId));
    }

}
