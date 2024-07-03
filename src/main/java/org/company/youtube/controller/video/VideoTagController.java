package org.company.youtube.controller.video;

import lombok.RequiredArgsConstructor;
import org.company.youtube.dto.video.VideoTagCreateDTO;
import org.company.youtube.dto.video.VideoTagDTO;
import org.company.youtube.service.video.VideoTagService;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/video-tag")
@RequiredArgsConstructor
public class VideoTagController {

    private final VideoTagService videoTagService;

//    1. Add tag to video. (USER and OWNER)
//        (video_id,tag_id) keladigan malumot.

    @PostMapping("/add/{videoId}")
    public HttpStatus add(@PathVariable("videoId") String videoId, @RequestBody VideoTagCreateDTO dto) {
        videoTagService.merge(videoId, dto.getTags());
        return HttpStatus.OK;
    }

//   2. Delete tag from vide (USER and OWNER)
//        (video_id,tag_id) keladigan malumot.

    @DeleteMapping("/delete/{videoId}/{tagId}")
    public HttpStatus delete(@PathVariable("videoId") String videoId, @PathVariable String tagId) {
        videoTagService.delete(videoId, tagId);
        return HttpStatus.OK;
    }

//   3. Get video Tag List by videoId
//       (id,video_id,tag(id,name),created_date)

    @GetMapping("/list/{videoId}")
    public ResponseEntity<PageImpl<VideoTagDTO>> get(@PathVariable("videoId") String videoId,
                                                     @RequestParam(value = "page", defaultValue = "1") int page,
                                                     @RequestParam(value = "size", defaultValue = "10") int size) {
        return ResponseEntity.ok().body(videoTagService.findAllByVideoId(videoId, page -1, size));

    }

}
