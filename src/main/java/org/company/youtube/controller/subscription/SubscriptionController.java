package org.company.youtube.controller.subscription;

import lombok.RequiredArgsConstructor;
import org.company.youtube.dto.ApiResponse;
import org.company.youtube.dto.record.subscription.SubscriptionRequest;
import org.company.youtube.dto.record.subscription.SubscriptionResponse;
import org.company.youtube.usecase.SubscriptionUseCase;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/subscription")
@RequiredArgsConstructor
public class SubscriptionController {

    private final SubscriptionUseCase<SubscriptionRequest, SubscriptionResponse> subscriptionUseCase;

//    1. Create User Subscription (USER)
//        channel_id,notification_type (keladigna parametr)

    @GetMapping("/create")
    public ResponseEntity<ApiResponse<SubscriptionResponse>> create(@RequestBody SubscriptionRequest request) {
        return ResponseEntity.ok().body(subscriptionUseCase.merge(request));
    }

    /*@GetMapping("/create") // this correct method "create"
    public ResponseEntity<ApiResponse<String>> create(@RequestBody SubscriptionRequest request) {
        return ResponseEntity.ok().body(subscriptionUseCase.merge(request));
    }*/

//    2. Change Subscription Status (USER)
//        channel_id,status

    @GetMapping("/change-status")
    public ResponseEntity<ApiResponse<?>> changeStatus(@RequestBody SubscriptionRequest.ChangeStatus request) {
        return ResponseEntity.ok().body(subscriptionUseCase.changeStatus(request));
    }

//    3. Change Subscription Notification type (USER)
//        channel_id,notification_type

    @GetMapping("/change-notification-type")
    public ResponseEntity<ApiResponse<SubscriptionResponse>> changeNotificationType(@RequestBody SubscriptionRequest request) {
        return ResponseEntity.ok().body(subscriptionUseCase.merge(request));
    }

//    4. Get User Subscription List (only Active) (murojat qilgan user)
//        SubscriptionInfo

    @GetMapping("/list")
    public ResponseEntity<ApiResponse<Page<SubscriptionResponse>>> subscriptionList(@RequestParam(defaultValue = "1") int page,
                                                                                    @RequestParam(defaultValue = "5") int size) {
        return ResponseEntity.ok().body(subscriptionUseCase.list(page - 1, size));
    }

//    5. Get User Subscription List By UserId (only Active) (ADMIN)
//        SubscriptionInfo + created_date

    @GetMapping("/list-by-user-id/{userId}")
    public ResponseEntity<ApiResponse<Page<SubscriptionResponse>>> subscriptionListByUserId(@RequestParam(defaultValue = "1") int page,
                                                                                            @RequestParam(defaultValue = "5") int size,
                                                                                            @PathVariable("userId") String userId) {
        return ResponseEntity.ok().body(subscriptionUseCase.listByUserId(page - 1, size,userId));
    }
}
