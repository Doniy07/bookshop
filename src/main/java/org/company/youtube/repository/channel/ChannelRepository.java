package org.company.youtube.repository.channel;

import org.company.youtube.entity.channel.ChannelEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.Optional;

public interface ChannelRepository extends CrudRepository<ChannelEntity, String>,
        PagingAndSortingRepository<ChannelEntity, String> {

    Page<ChannelEntity> findByProfileIdOrderByCreatedDateDesc(String userId, Pageable pageable);

    @Query("select c from ChannelEntity c where c.id = ?1 and c.status = 'ACTIVE'")
    Optional<ChannelEntity> findByIdAndStatusActive(String channelId);

}
