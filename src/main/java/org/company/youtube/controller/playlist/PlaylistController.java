package org.company.youtube.controller.playlist;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.company.youtube.dto.playlist.PlaylistCreateDTO;
import org.company.youtube.dto.playlist.PlaylistDTO;
import org.company.youtube.service.playlist.PlaylistService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
