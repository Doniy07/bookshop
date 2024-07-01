package org.company.youtube.service.channel;

import lombok.RequiredArgsConstructor;
import org.company.youtube.dto.attach.AttachDTO;
import org.company.youtube.dto.channel.ChannelCreateDTO;
import org.company.youtube.dto.channel.ChannelDTO;
import org.company.youtube.dto.channel.ChannelUpdateDTO;
import org.company.youtube.dto.playlist.PlaylistDTO;
import org.company.youtube.entity.channel.ChannelEntity;
import org.company.youtube.entity.playlist.PlaylistEntity;
import org.company.youtube.enums.ChannelStatus;
import org.company.youtube.enums.ProfileRole;
import org.company.youtube.exception.AppBadException;
import org.company.youtube.repository.channel.ChannelRepository;
import org.company.youtube.service.attach.AttachService;
import org.company.youtube.util.SecurityUtil;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ChannelService {

    private final ChannelRepository channelRepository;
    private final AttachService attachService;

    public ChannelDTO create(ChannelCreateDTO dto) {

        ChannelEntity entity = new ChannelEntity();
        entity.setName(dto.getName());
        entity.setDescription(dto.getDescription());
        entity.setBannerId(dto.getBannerId());
        entity.setProfileId(SecurityUtil.getProfileId());
        entity.setPhotoId(dto.getPhotoId());

        channelRepository.save(entity);
        return toDTO(entity);
    }

    private ChannelDTO toDTO(ChannelEntity entity) {

        ChannelDTO dto = new ChannelDTO();

        dto.setName(entity.getName());
        dto.setDescription(entity.getDescription());
        dto.setBannerId(entity.getBannerId());
        dto.setProfileId(entity.getProfileId());
        dto.setPhotoId(entity.getPhotoId());
        return dto;
    }

    public ChannelEntity checkOwner(String channelId) {
        ChannelEntity entity = getChannelById(channelId);
        if (!entity.getProfileId().equals(SecurityUtil.getProfileId())) {
            throw new AppBadException("You are not owner of this channel");
        }
        return entity;
    }
    public ChannelDTO update(String channelId, ChannelUpdateDTO dto) {
        ChannelEntity entity = checkOwner(channelId);
        entity.setName(dto.getName());
        entity.setDescription(dto.getDescription());
        channelRepository.save(entity);
        return toDTO(entity);
    }

    public ChannelDTO updatePhoto(String channelId, MultipartFile file) {
        ChannelEntity entity = checkOwner(channelId);
        attachService.deleteAttach(entity.getPhotoId());
        AttachDTO attachDTO = attachService.saveAttach(file);
        entity.setPhotoId(attachDTO.getId());
        channelRepository.save(entity);
        return toDTO(entity);
    }

    public ChannelDTO updateBanner(String channelId, MultipartFile file) {
        ChannelEntity entity = checkOwner(channelId);
        attachService.deleteAttach(entity.getBannerId());
        AttachDTO attachDTO = attachService.saveAttach(file);
        entity.setBannerId(attachDTO.getId());
        channelRepository.save(entity);
        return toDTO(entity);
    }

    public ChannelEntity getChannelById(String channelId) {
        Optional<ChannelEntity> optional = channelRepository.findById(channelId);
        if (optional.isEmpty()) {
            throw new AppBadException("Channel not found");
        }
        return optional.get();
    }

    public ChannelDTO getChannel(String channelId) {
        return toDTO(getChannelById(channelId));
    }

    public ChannelDTO changeStatus(String channelId, ChannelStatus status) {
        ChannelEntity entity = checkOwner(channelId);
        if(!SecurityUtil.getProfile().getRole().equals(ProfileRole.ROLE_ADMIN)){
            throw new AppBadException("You are not ADMIN");
        }
        if (!entity.getStatus().equals(ChannelStatus.ACTIVE)){
            throw new AppBadException("Channel already BLOCKED");
        }
        entity.setStatus(ChannelStatus.BLOCK);
        channelRepository.save(entity);
        return toDTO(entity);
    }


//    public ChannelDTO getUserChannelList(int page, int size, String userId) {
//        Pageable pageable = PageRequest.of(page, size, Sort.by("createdDate").descending());
//        Page<ChannelEntity> mapperList = channelRepository.getUserChannelList(userId, pageable);
//        return new PageImpl<>(iterateStream(mapperList.getContent()), mapperList.getPageable(), mapperList.getTotalElements());
//    }

    private List<ChannelDTO> iterateStream(List<ChannelEntity> articles) {
        return articles.stream()
                .map(this::toDTO)
                .toList();
    }
}
