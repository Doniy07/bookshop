package org.company.youtube.controller.playlist;

import lombok.RequiredArgsConstructor;
import org.company.youtube.dto.playlist.PlaylistVideoDTO;
import org.company.youtube.dto.playlist.PlaylistVideoRequestDTO;
import org.company.youtube.dto.video.VideoDTO;
import org.company.youtube.service.playlist.PlaylistVideoService;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/playlist-video")
@RequiredArgsConstructor
public class PlaylistVideoController {

    private final PlaylistVideoService playlistVideoService;

//    1. Create (User and Owner)
//        (playlist_id,video_id, order_num) front dan keladigan malumotlar

    @PostMapping("/create")
    public ResponseEntity<PlaylistVideoDTO> create(@RequestBody PlaylistVideoRequestDTO dto) {
        return ResponseEntity.ok().body(playlistVideoService.merge(dto));
    }

//    2. Update
//        (playlist_id,video_id, order_num) front dan keladigan malumotlar

    @PutMapping("/update")
    public ResponseEntity<PlaylistVideoDTO> update(@RequestBody PlaylistVideoRequestDTO dto) {
        return ResponseEntity.ok().body(playlistVideoService.merge(dto));
    }

//    3. Delete PlayListVideo
//          (playlist_id,video_id) front dan keladigan malumotlar

    @DeleteMapping("/delete")  // TODO qayta korib chiqish kerak
    public HttpStatus delete(@RequestBody PlaylistVideoRequestDTO dto) {
        playlistVideoService.delete(dto);
        return HttpStatus.OK;
    }

//   4. Get Video list by playListId (video status published)
//        PlaylistVideoInfo

    @GetMapping("/get-video-list-by-playlist-id/{playlistId}")
    public ResponseEntity<PageImpl<PlaylistVideoDTO>> getVideoListByPlaylistId(@PathVariable String playlistId,
                                                                      @RequestParam(value = "page", defaultValue = "1") int page,
                                                                      @RequestParam(value = "size", defaultValue = "10") int size) {
        return ResponseEntity.ok().body(playlistVideoService.getVideoListByPlaylistId(playlistId, page, size));
    }
}
