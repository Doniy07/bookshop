package org.company.youtube.controller.playlist;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.company.youtube.dto.playlist.PlaylistCreateDTO;
import org.company.youtube.dto.playlist.PlaylistDTO;
import org.company.youtube.dto.playlist.PlaylistUpdateDTO;
import org.company.youtube.enums.PlaylistStatus;
import org.company.youtube.mapper.PlaylistInfoMapper;
import org.company.youtube.service.playlist.PlaylistService;
import org.json.JSONArray;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/playlist")
@RequiredArgsConstructor
public class PlaylistController {

    private final PlaylistService playlistService;

//    1. Create Playlist (USER)

    @PostMapping("/create")
    public ResponseEntity<PlaylistDTO> create(@Valid @RequestBody PlaylistCreateDTO dto) {
        return ResponseEntity.ok().body(playlistService.create(dto));
    }

//    2. Update Playlist(USER and OWNER)

    @PostMapping("/update/{playlistId}")
    public ResponseEntity<PlaylistDTO> update(@PathVariable String playlistId,
                                              @Valid @RequestBody PlaylistUpdateDTO dto) {
        return ResponseEntity.ok().body(playlistService.update(playlistId, dto));
    }

//    3. Change Playlist Status (USER and OWNER)

    @PostMapping("/change-status/{playlistId}")
    public ResponseEntity<PlaylistDTO> changeStatus(@PathVariable String playlistId,
                                                    @Valid @RequestBody PlaylistStatus status) {
        return ResponseEntity.ok().body(playlistService.changeStatus(playlistId, status));
    }

//    4. Delete Playlist (USER and OWNER, ADMIN)

    @PostMapping("/delete/{playlistId}")
    public ResponseEntity<PlaylistDTO> delete(@PathVariable String playlistId) {
        return ResponseEntity.ok().body(playlistService.delete(playlistId));
    }

//      5. Playlist Pagination (ADMIN)
//        PlaylistInfoMapper

    @PostMapping("/adm/pagination")
    public ResponseEntity<PageImpl<PlaylistInfoMapper>> pagination(@RequestParam(value = "page", defaultValue = "1") int page,
                                                                   @RequestParam(value = "size", defaultValue = "10") int size) {
        return ResponseEntity.ok().body(playlistService.pagination(page - 1, size));
    }

//    6. Playlist List By UserId (order by order number desc) (ADMIN)
//        PlaylistInfoMapper


    @GetMapping("/adm/{userId}")
    public ResponseEntity<PageImpl<PlaylistInfoMapper>> getUserPlaylist(@RequestParam(value = "page", defaultValue = "1") int page,
                                                                        @RequestParam(value = "size", defaultValue = "10") int size,
                                                                        @PathVariable("userId") String userId) {
        return ResponseEntity.ok().body(playlistService.getUserPlaylist(page - 1, size, userId));
    }

//    7. Get User Playlist (order by order number desc) (murojat qilgan user ni)
//        PlayListShortInfo

    @GetMapping("/user/{userId}")
    public String getPlaylistsByUserId(@PathVariable String userId) {
        JSONArray playlists = playlistService.getPlaylistsByUserId(userId);
        return playlists.toString(4);
    }


//    8. Get Channel Play List By ChannelKey (order by order_num desc) (only Public)
//        PlayListShortInfo

    @GetMapping("/channel/{channelId}")
    public String getChannelPlaylist(@PathVariable String channelId) {
        JSONArray playlists = playlistService.getChannelPlaylist(channelId);
        return playlists.toString(4);
    }

//     9. Get Playlist by id
//        id,name,video_count, total_view_count (shu play listdagi videolarni ko'rilganlar soni),
//        last_update_date

    @GetMapping("/{playlistId}")
    public String getPlaylistById(@PathVariable String playlistId) {
        JSONArray playlists = playlistService.getPlaylist(playlistId);
        return playlists.toString(4);
    }

}
