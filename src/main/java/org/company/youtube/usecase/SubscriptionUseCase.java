package org.company.youtube.usecase;

import org.company.youtube.dto.ApiResponse;
import org.company.youtube.dto.record.subscription.SubscriptionRequest;
import org.springframework.data.domain.Page;

public interface SubscriptionUseCase<REQUEST, RESPONSE> {
    ApiResponse<RESPONSE> merge(REQUEST request);

    ApiResponse<RESPONSE> changeStatus(SubscriptionRequest.ChangeStatus request);

    ApiResponse<Page<RESPONSE>> list(int page, int size);

    ApiResponse<Page<RESPONSE>> listByUserId(int page, int size, String userId);
}
