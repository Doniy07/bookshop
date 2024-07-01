package org.company.youtube.service.channel;

import lombok.RequiredArgsConstructor;
import org.company.youtube.dto.attach.AttachDTO;
import org.company.youtube.dto.channel.ChannelCreateDTO;
import org.company.youtube.dto.channel.ChannelDTO;
import org.company.youtube.dto.channel.ChannelUpdateDTO;
import org.company.youtube.entity.channel.ChannelEntity;
import org.company.youtube.entity.profile.ProfileEntity;
import org.company.youtube.enums.ChannelStatus;
import org.company.youtube.enums.ProfileRole;
import org.company.youtube.exception.AppBadException;
import org.company.youtube.repository.channel.ChannelRepository;
import org.company.youtube.service.attach.AttachService;
import org.company.youtube.util.SecurityUtil;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

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

    public ChannelDTO update(ChannelUpdateDTO dto) {
        ChannelEntity entity = getChannelEntityByProfileId();
        entity.setName(dto.getName());
        entity.setDescription(dto.getDescription());
        channelRepository.save(entity);
        return toDTO(entity);
    }

    public ChannelEntity getChannelEntityByProfileId() {
        Optional<ChannelEntity> optional = channelRepository.findByProfileId(SecurityUtil.getProfileId());
        if (optional.isEmpty()) {
            throw new AppBadException("Channel not found");
        }
        return optional.get();
    }

    public ChannelDTO updatePhoto(MultipartFile file) {
        ChannelEntity entity = getChannelEntityByProfileId();
        attachService.deleteAttach(entity.getPhotoId());
        AttachDTO attachDTO = attachService.saveAttach(file);
        entity.setPhotoId(attachDTO.getId());
        channelRepository.save(entity);
        return toDTO(entity);
    }

    public ChannelDTO updateBanner(MultipartFile file) {
        ChannelEntity entity = getChannelEntityByProfileId();
        attachService.deleteAttach(entity.getBannerId());
        AttachDTO attachDTO = attachService.saveAttach(file);
        entity.setBannerId(attachDTO.getId());
        channelRepository.save(entity);
        return toDTO(entity);
    }

    public ChannelEntity getChannel(String channelId) {
        Optional<ChannelEntity> optional = channelRepository.findById(channelId);
        if (optional.isEmpty()) {
            throw new AppBadException("Channel not found");
        }
        return optional.get();
    }

    public ChannelDTO getChannelById(String channelId) {
        return toDTO(getChannel(channelId));
    }

    public ChannelDTO changeStatus(String channelId) {
        ProfileEntity profile = SecurityUtil.getProfile();
        ChannelEntity entity;
        if (!profile.getRole().equals(ProfileRole.ROLE_ADMIN)){
            entity = getChannelEntityByProfileId();
        }else {
            entity = getChannel(channelId);
        }
        if (!entity.getStatus().equals(ChannelStatus.ACTIVE)){
            throw new AppBadException("Channel already BLOCKED");
        }
        entity.setStatus(ChannelStatus.BLOCK);
        channelRepository.save(entity);
        return toDTO(entity);
    }
}
