package org.company.youtube.repository.subscription;

import org.company.youtube.entity.subscription.SubscriptionEntity;
import org.company.youtube.mapper.SubscriptionInfoMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface SubscriptionRepository extends JpaRepository<SubscriptionEntity,String> {

    @Query(" select s " +
            " from SubscriptionEntity s " +
            " where s.profileId = :profileId " +
            " and s.channelId = :channelId " +
            " and s.status = 'ACTIVE' ")
    SubscriptionEntity findByProfileIdAndChannelId(@Param("profileId") String profileId,
                                                   @Param("channelId") String channelId);

    @Query("select " +
            " s.id as subscriptionId, " +
            " ch.id as channelId, " +
            " ch.name as channelName, " +
            " ch.profileId as channelProfileId, " +
            " s.type as notificationType, " +
            " case when :admin = true then s.createdDate else null end as createdDate " +
            "from SubscriptionEntity s " +
            "inner join s.channel ch " +
            "where s.profileId = :profileId " +
            "and s.status = 'ACTIVE'")
    Page<SubscriptionInfoMapper> findAllByProfileId(@Param("profileId") String profileId,
                                                    @Param("admin") Boolean admin, Pageable pageable);
}

