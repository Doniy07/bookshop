package org.company.youtube.repository.channel;

import org.company.youtube.entity.channel.ChannelEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.Optional;

public interface ChannelRepository extends CrudRepository<ChannelEntity, String>,
        PagingAndSortingRepository<ChannelEntity, String> {

    Optional<ChannelEntity> findByProfileId(String profileId);
}
