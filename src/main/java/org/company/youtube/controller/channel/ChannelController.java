package org.company.youtube.controller.channel;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.company.youtube.dto.channel.ChannelCreateDTO;
import org.company.youtube.dto.channel.ChannelDTO;
import org.company.youtube.dto.channel.ChannelUpdateDTO;
import org.company.youtube.service.channel.ChannelService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/channel")
@RequiredArgsConstructor
public class ChannelController {

    private final ChannelService channelService;

//     1. Create Channel (USER)

    @PostMapping("/create")
    public ResponseEntity<ChannelDTO> create(@Valid @RequestBody ChannelCreateDTO dto) {
        return ResponseEntity.ok().body(channelService.create(dto));
    }

//    2. Update Channel ( USER and OWNER)

    @PostMapping("/update/{channelId}")
    public ResponseEntity<ChannelDTO> update(@PathVariable String channelId,
                                             @Valid @RequestBody ChannelUpdateDTO dto) {
        return ResponseEntity.ok().body(channelService.update(channelId, dto));
    }

//    3. Update Channel photo ( USER and OWNER)

    @PostMapping("/update-photo/{channelId}")
    public ResponseEntity<ChannelDTO> updatePhoto(@PathVariable String channelId,
                                                  @RequestParam("image") MultipartFile file) {
        return ResponseEntity.ok().body(channelService.updatePhoto(channelId, file));
    }

//    4. Update Channel banner ( USER and OWNER)

    @PostMapping("/update-banner/{channelId}")
    public ResponseEntity<ChannelDTO> updateBanner(@PathVariable String channelId,
                                                   @RequestParam("image") MultipartFile file) {
        return ResponseEntity.ok().body(channelService.updateBanner(channelId, file));
    }

    //    6. Get Channel By Id
    @GetMapping("/channel/{channelId}")
    public ResponseEntity<ChannelDTO> getChannelById(@PathVariable String channelId) {
        return ResponseEntity.ok().body(channelService.getChannel(channelId));
    }

    //        7. Change Channel Status (ADMIN,USER and OWNER)
    @PutMapping("/change-status/{channelId}")
    public ResponseEntity<ChannelDTO> changeStatus(@PathVariable String channelId) {
        return ResponseEntity.ok().body(channelService.changeStatus(channelId));
    }

}
