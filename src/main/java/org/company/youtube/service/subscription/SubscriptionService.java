package org.company.youtube.service.subscription;

import lombok.RequiredArgsConstructor;
import org.company.youtube.dto.ApiResponse;
import org.company.youtube.dto.channel.ChannelDTO;
import org.company.youtube.dto.record.subscription.SubscriptionRequest;
import org.company.youtube.dto.record.subscription.SubscriptionResponse;
import org.company.youtube.entity.subscription.SubscriptionEntity;
import org.company.youtube.enums.ProfileRole;
import org.company.youtube.mapper.SubscriptionInfoMapper;
import org.company.youtube.repository.subscription.SubscriptionRepository;
import org.company.youtube.usecase.SubscriptionUseCase;
import org.company.youtube.util.SecurityUtil;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.function.Function;

@Service
@RequiredArgsConstructor
public class SubscriptionService implements SubscriptionUseCase<SubscriptionRequest, SubscriptionResponse> {

    private final SubscriptionRepository subscriptionRepository;

    @Override
    public ApiResponse<SubscriptionResponse> merge(SubscriptionRequest subscriptionRequest) {
        SubscriptionEntity check = subscriptionRepository.findByProfileIdAndChannelId(SecurityUtil.getProfileId(), subscriptionRequest.channelId());
        if (check != null) {
            check.setType(subscriptionRequest.type());
            subscriptionRepository.save(check);
            SubscriptionResponse response = mapToResponse().apply(check);
            return ApiResponse.ok(response);
        }
        SubscriptionEntity entity = subscriptionRepository.save(mapToEntity().apply(subscriptionRequest));
        SubscriptionResponse response = mapToResponse().apply(entity);
        return ApiResponse.ok(response);

    }

/*    @Override  // this is correct method "create"
    public ApiResponse<String> create(SubscriptionRequest subscriptionRequest) {
        SubscriptionEntity check = subscriptionRepository.findByProfileIdAndChannelId(SecurityUtil.getProfileId(), subscriptionRequest.channelId());
        String oldType = String.valueOf(check.getType());
        if (check != null) {

            check.setType(subscriptionRequest.status());
            subscriptionRepository.save(check);
            return new ApiResponse<>("Subscription status updated from: " + oldType + " to: " + subscriptionRequest.status() + " successfully" , 200);
        }
        SubscriptionEntity entity = subscriptionRepository.save(mapToEntity().apply(subscriptionRequest));
        return new ApiResponse<>("Subscription status created by name: " +  subscriptionRequest.status() + " and with id: " + entity.getId() + " successfully" , 200);

    }*/

    @Override
    public ApiResponse<SubscriptionResponse> changeStatus(SubscriptionRequest.ChangeStatus request) {
        SubscriptionEntity check = subscriptionRepository.findByProfileIdAndChannelId(SecurityUtil.getProfileId(), request.channelId());
        if (check != null) {
            check.setStatus(request.status());
            subscriptionRepository.save(check);
            SubscriptionResponse response = mapToResponse().apply(check);
            return ApiResponse.ok(response);
        }
        return new ApiResponse<>("Subscription not found", 404);
    }

    @Override
    public ApiResponse<Page<SubscriptionResponse>> list(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);

        Page<SubscriptionInfoMapper> pageList = subscriptionRepository
                .findAllByProfileId(SecurityUtil.getProfileId(), isAdmin(), pageable);

        List<SubscriptionResponse> list = pageList.getContent().stream().map(mapperMapToResponse()).toList();

        return ApiResponse.ok(new PageImpl<>(list, pageList.getPageable(), pageList.getTotalElements()));
    }

    @Override
    public ApiResponse<Page<SubscriptionResponse>> listByUserId(int page, int size, String userId) {

        Pageable pageable = PageRequest.of(page, size);

        Page<SubscriptionInfoMapper> pageList = subscriptionRepository
                .findAllByProfileId(userId, isAdmin(), pageable);

        List<SubscriptionResponse> list = pageList.getContent().stream().map(mapperMapToResponse()).toList();

        return ApiResponse.ok(new PageImpl<>(list, pageList.getPageable(), pageList.getTotalElements()));
    }

    private Boolean isAdmin() {
        return SecurityUtil.getProfile().getRole().equals(ProfileRole.ROLE_ADMIN);
    }

    private Function<SubscriptionRequest, SubscriptionEntity> mapToEntity() {
        return entity -> SubscriptionEntity.builder().profileId(SecurityUtil.getProfileId()).channelId(entity.channelId()).type(entity.type()).build();
    }

    private Function<SubscriptionEntity, SubscriptionResponse> mapToResponse() {
        return response -> SubscriptionResponse.builder().id(response.getId()).profileId(SecurityUtil.getProfileId()).channelId(response.getChannelId()).unsubscribeDate(response.getUnsubscribeDate() != null ? response.getUnsubscribeDate() : null).status(response.getStatus() != null ? response.getStatus() : null).type(response.getType()).createdDate(response.getCreatedDate() != null ? response.getCreatedDate() : null).build();
    }

    private Function<SubscriptionInfoMapper, SubscriptionResponse> mapperMapToResponse() {
        return response -> SubscriptionResponse.builder()
                .id(response.getSubscriptionId())
                .channel(ChannelDTO.builder()
                        .id(response.getChannelId())
                        .name(response.getChannelName())
                        .photoId(response.getChannelPhotoId())
                        .build())
                .type(response.getNotificationType())
                .createdDate(response.getCreatedDate() != null ? response.getCreatedDate() : null)
                .build();
    }
}
