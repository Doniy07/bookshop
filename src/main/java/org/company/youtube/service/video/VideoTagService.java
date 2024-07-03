package org.company.youtube.service.video;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.company.youtube.dto.tag.TagDTO;
import org.company.youtube.dto.video.VideoTagDTO;
import org.company.youtube.entity.video.VideoTagEntity;
import org.company.youtube.mapper.VideoTagMapper;
import org.company.youtube.repository.video.VideoTagRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class VideoTagService {

    private final VideoTagRepository videoTagRepository;

    @Transactional
    public void merge(String videoId, List<String> newList) {
        Objects.requireNonNull(newList, "New types list must not be null");
        List<String> oldList = videoTagRepository.findAllTagsIdByVideoId(videoId);
        if (oldList.isEmpty()) {
            bulkInsert(videoId, newList);
            return;
        }

        Set<String> toDelete = new HashSet<>(oldList);
        newList.forEach(toDelete::remove);

        Set<String> toAdd = new HashSet<>(newList);
        oldList.forEach(toAdd::remove);

        if (!toAdd.isEmpty()) {
            bulkInsert(videoId, new ArrayList<>(toAdd));
        }

        if (!toDelete.isEmpty()) {
            videoTagRepository.deleteAllByVideoIdAndTagList(videoId, new ArrayList<>(toDelete));
        }

        /*Objects.requireNonNull(newList);
        List<Integer> oldLists = articleTypeRepository.findAllTypesIdByArticleId(articleId);
        oldLists.forEach(oldId -> {
            if (!newList.contains(oldId)) {
                articleTypeRepository.deleteByArticleIdAndTypesId(articleId, oldId);
            }
        });
        newList.forEach(newId -> {
            if (!oldLists.contains(newId)) {
                create(articleId, newId);
            }
        });*/
    }

    private void bulkInsert(String videoId, List<String> tagsId) {
        List<VideoTagEntity> entities = tagsId.stream()
                .map(tagId -> {
                    VideoTagEntity entity = new VideoTagEntity();
                    entity.setVideoId(videoId);
                    entity.setTagId(tagId);
                    return entity;
                })
                .collect(Collectors.toList());

        videoTagRepository.saveAll(entities);
    }

    public void delete(String videoId, String tagId) {
        videoTagRepository.deleteByVideoIdAndTagId(videoId, tagId);
    }

    public PageImpl<VideoTagDTO> findAllByVideoId(String videoId, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<VideoTagMapper> mapperList = videoTagRepository.findByVideoId(videoId, pageable);
        return new PageImpl<>(iteratorInfo(mapperList.getContent()), mapperList.getPageable(), mapperList.getTotalElements());
    }

    private List<VideoTagDTO> iteratorInfo(List<VideoTagMapper> mappers) {
        List<VideoTagDTO> list = mappers
                .stream()
                .map(mapper -> VideoTagDTO.builder()
                        .id(mapper.getId())
                        .videoId(mapper.getVideoId())
                        .tag(TagDTO.builder()
                                .id(mapper.getTagId())
                                .name(mapper.getTagName())
                                .build())
                        .createdDate(mapper.getCreatedDate())
                        .build())
                .toList();

        return list;
    }
}
