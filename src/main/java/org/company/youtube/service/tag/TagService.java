package org.company.youtube.service.tag;

import lombok.RequiredArgsConstructor;
import org.company.youtube.dto.tag.TagCreateDTO;
import org.company.youtube.dto.tag.TagDTO;
import org.company.youtube.entity.tag.TagEntity;
import org.company.youtube.exception.AppBadException;
import org.company.youtube.repository.tag.TagRepository;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TagService {

    private final TagRepository tagRepository;

    public TagDTO create(TagCreateDTO categoryDTO) {
        TagEntity entity = new TagEntity();
        entity.setName(categoryDTO.getName());
        tagRepository.save(entity);
        return toDTO(entity);
    }

    private TagDTO toDTO(TagEntity entity) {
        TagDTO dto = new TagDTO();
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setCreatedDate(entity.getCreatedDate());
        return dto;
    }

    public Boolean update(Integer id, TagCreateDTO dto) {
        TagEntity entity = getTag(id);
        entity.setName(dto.getName());
        tagRepository.save(entity);
        return true;
    }

    public TagEntity getTag(Integer id) {
        return tagRepository.findById(id).orElseThrow(() -> new AppBadException("Tag not found"));
    }

    public Boolean delete(Integer id) {
        TagEntity entity = getTag(id);
        entity.setVisible(Boolean.FALSE);
        tagRepository.save(entity);
        return true;
    }

    public PageImpl<TagDTO> pagination(int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("createdDate").descending());
        Page<TagEntity> mapperList = tagRepository.findAll(pageable);
        return new PageImpl<>(iterateStream(mapperList.getContent()), mapperList.getPageable(), mapperList.getTotalElements());
    }

    private List<TagDTO> iterateStream(List<TagEntity> categories) {
        return categories.stream()
                .map(this::toDTO)
                .toList();
    }

}
