package org.company.youtube.controller.channel;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.company.youtube.dto.channel.ChannelCreateDTO;
import org.company.youtube.dto.channel.ChannelDTO;
import org.company.youtube.dto.channel.ChannelUpdateDTO;
import org.company.youtube.service.channel.ChannelService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/channel")
@AllArgsConstructor
public class ChannelController {

    private final ChannelService channelService;

//     1. Create Channel (USER)

    @PostMapping("/create")
    public ResponseEntity<ChannelDTO> create(@Valid @RequestBody ChannelCreateDTO dto) {
        return ResponseEntity.ok().body(channelService.create(dto));
    }

//    2. Update Channel ( USER and OWNER)

    @PostMapping("/update")
    public ResponseEntity<ChannelDTO> update(@Valid @RequestBody ChannelUpdateDTO dto) {
        return ResponseEntity.ok().body(channelService.update(dto));
    }

//    3. Update Channel photo ( USER and OWNER)

    @PostMapping("/update-photo")
    public ResponseEntity<ChannelDTO> updatePhoto(@RequestParam("image") MultipartFile file) {
        return ResponseEntity.ok().body(channelService.updatePhoto(file));
    }

//    4. Update Channel banner ( USER and OWNER)

    @PostMapping("/update-banner")
    public ResponseEntity<ChannelDTO> updateBanner(@RequestParam("image") MultipartFile file) {
        return ResponseEntity.ok().body(channelService.updateBanner(file));
    }

//    6. Get Channel By Id
    @GetMapping("/channel/{id}")
    public ResponseEntity<ChannelDTO> getChannelById(@PathVariable String id) {
        return ResponseEntity.ok().body(channelService.getChannelById(id));
    }

//        7. Change Channel Status (ADMIN,USER and OWNER)
    @PutMapping("/change-status/{id}")
    public ResponseEntity<ChannelDTO> changeStatus(@PathVariable String id) {
        return ResponseEntity.ok().body(channelService.changeStatus(id));
    }

}
