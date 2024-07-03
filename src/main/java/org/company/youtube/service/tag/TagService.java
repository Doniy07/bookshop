package org.company.youtube.service.tag;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.company.youtube.dto.tag.TagCreateDTO;
import org.company.youtube.dto.tag.TagDTO;
import org.company.youtube.entity.tag.TagEntity;
import org.company.youtube.exception.AppBadException;
import org.company.youtube.repository.tag.TagRepository;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class TagService {

    private final TagRepository tagRepository;


    public TagDTO create(TagCreateDTO dto) {
        TagEntity entity = new TagEntity();
        entity.setName(dto.getName());
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

    public void create(List<String> newList) {

        List<String> oldList = tagRepository.findAllName();

        Set<String> toAdd = new HashSet<>(newList);
        for (String s : oldList) {
            toAdd.remove(s);
        }

        if (!toAdd.isEmpty()) {
            toAdd.forEach(name -> {
                TagEntity entity = new TagEntity();
                entity.setName(name);
                tagRepository.save(entity);
            });
        }
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

    public List<String> getTagsId(List<String> tagNames) {
        List<TagEntity> tagEntities = tagRepository.findAllByNameIn(tagNames);
        return tagEntities.stream().map(TagEntity::getId).toList();
    }
}
