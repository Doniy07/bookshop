package org.company.youtube.controller.video;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.company.youtube.dto.video.VideoCreateDTO;
import org.company.youtube.dto.video.VideoDTO;
import org.company.youtube.dto.video.VideoUpdateDTO;
import org.company.youtube.enums.VideoStatus;
import org.company.youtube.service.video.VideoService;
import org.json.JSONArray;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/video")
@RequiredArgsConstructor
public class VideoController {

    private final VideoService videoService;

//        1. Create Video (USER)

    @PostMapping("/create")
    public ResponseEntity<VideoCreateDTO> create(@Valid @RequestBody VideoCreateDTO dto) {
        return ResponseEntity.ok().body(videoService.create(dto));
    }

//    2. Update Video Detail (USER and OWNER)

    @PutMapping("/update/{videoId}")
    public ResponseEntity<VideoUpdateDTO> update(@PathVariable("videoId") String videoId,
                                                 @Valid @RequestBody VideoUpdateDTO dto) {
        return ResponseEntity.ok().body(videoService.update(videoId, dto));
    }

//    3. Change Video Status (USER and OWNER)

    @PutMapping("/change-status/{videoId}")
    public ResponseEntity<VideoDTO> changeVideoStatus(@PathVariable("videoId") String videoId,
                                                      @RequestParam("status") VideoStatus status) {
        return ResponseEntity.ok().body(videoService.changeStatus(videoId, status));
    }

//    4. Increase video_view Count by id

    @PutMapping("/increase-view-count/{videoId}")
    public ResponseEntity<VideoDTO> increaseViewCount(@PathVariable("videoId") String videoId) {
        return ResponseEntity.ok().body(videoService.increaseViewCount(videoId));
    }

    //    4. Increase video_shared Count by id

    @PutMapping("/increase-shared-count/{videoId}")
    public ResponseEntity<VideoDTO> increaseShareCount(@PathVariable("videoId") String videoId) {
        return ResponseEntity.ok().body(videoService.increaseShareCount(videoId));
    }

//    5. Get Video Pagination by CategoryId
//         VideoShortInfo

    @GetMapping("/pagination/{categoryId}")
    public ResponseEntity<PageImpl<VideoDTO>> paginationByCategoryId(@PathVariable("categoryId") String categoryId,
                                                                     @RequestParam(value = "page", defaultValue = "1") int page,
                                                                     @RequestParam(value = "size", defaultValue = "10") int size) {
        return ResponseEntity.ok().body(videoService.paginationByCategoryId(categoryId, page - 1, size));
    }

//    6. Search video by Title
//        VideoShortInfo

    @GetMapping("/search/{title}")
    public ResponseEntity<PageImpl<VideoDTO>> searchByTitle(@PathVariable("title") String title,
                                                            @RequestParam(value = "page", defaultValue = "1") int page,
                                                            @RequestParam(value = "size", defaultValue = "10") int size) {
        return ResponseEntity.ok().body(videoService.searchByTitle(title, page - 1, size));
    }

//    7. Get video by tag_id with pagination
//            VideoShortInfo

    @GetMapping("/pagination/{tagId}")
    public ResponseEntity<PageImpl<VideoDTO>> paginationByTagId(@PathVariable("tagId") String tagId,
                                                                @RequestParam(value = "page", defaultValue = "1") int page,
                                                                @RequestParam(value = "size", defaultValue = "10") int size) {
        return ResponseEntity.ok().body(videoService.paginationByTagId(tagId, page - 1, size));
    }

//    8. Get Video By id (If Status PRIVATE allow only for OWNER or ADMIN)
//        VideoFullInfo

    @GetMapping("/{videoId}")
    public String getVideoById(@PathVariable("videoId") String videoId) {
        JSONArray playlists = videoService.getVideoById(videoId);
        return playlists.toString(4);
    }

//    9. Get Video List Pagination (ADMIN)
//        (VideoShortInfo + owner (is,name,surname) + playlist (id,name))

    @GetMapping("/pagination/video-list")
    public ResponseEntity<PageImpl<VideoDTO>> pagination(@RequestParam(value = "page", defaultValue = "1") int page,
                                                         @RequestParam(value = "size", defaultValue = "10") int size) {
        return ResponseEntity.ok().body(videoService.videoList(page - 1, size));
    }

//    10. Get Channel Video List Pagination (created_date desc)
//         VideoPlayListInfo
//         example: https://www.youtube.com/channel/UCFoy0KOV9sihL61PJSh7x1g/videos

    @GetMapping("/pagination/channel-video-list/{channelId}")
    public ResponseEntity<PageImpl<VideoDTO>> paginationByChannelId(@PathVariable("channelId") String channelId,
                                                                    @RequestParam(value = "page", defaultValue = "1") int page,
                                                                    @RequestParam(value = "size", defaultValue = "10") int size) {
        return ResponseEntity.ok().body(videoService.paginationByChannelId(channelId, page - 1, size));
    }

}
