package org.company.youtube.controller.playlist;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.company.youtube.dto.channel.ChannelDTO;
import org.company.youtube.dto.channel.ChannelUpdateDTO;
import org.company.youtube.dto.playlist.PlaylistCreateDTO;
import org.company.youtube.dto.playlist.PlaylistDTO;
import org.company.youtube.dto.playlist.PlaylistUpdateDTO;
import org.company.youtube.enums.PlaylistStatus;
import org.company.youtube.service.playlist.PlaylistService;
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

    @PostMapping("/update/{playlistId}")
    public ResponseEntity<PlaylistDTO> changeStatus(@PathVariable String playlistId,
                                              @Valid @RequestBody PlaylistStatus status) {
        return ResponseEntity.ok().body(playlistService.changeStatus(playlistId, status));
    }
}
