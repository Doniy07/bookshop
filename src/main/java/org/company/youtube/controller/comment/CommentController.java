package org.company.youtube.controller.comment;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.company.youtube.dto.ApiResponse;
import org.company.youtube.dto.record.comment.CommentRequest;
import org.company.youtube.dto.record.comment.CommentResponse;
import org.company.youtube.usecase.CommentUseCase;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/comment")
@RequiredArgsConstructor
public class CommentController {

    private final CommentUseCase<CommentRequest, CommentResponse> commentUseCase;

//    1. Crate Comment (USER)

    @PostMapping("/create")
    public ResponseEntity<ApiResponse<CommentResponse>> create(@Valid @RequestBody CommentRequest request) {
        return ResponseEntity.ok().body(commentUseCase.create(request));
    }

//    2. Update Comment (USER AND OWNER)

    @PutMapping("/update/{id}")
    public ResponseEntity<ApiResponse<CommentResponse>> update(@PathVariable("id") String id, @Valid @RequestBody CommentRequest.CommentUpdate request) {
        return ResponseEntity.ok().body(commentUseCase.update(id, request));
    }

//    3. Delete Comment (USER AND OWNER, ADMIN)

    @PutMapping("/delete/{id}")
    public ResponseEntity<ApiResponse<String>> delete(@PathVariable("id") String id) {
        return ResponseEntity.ok().body(commentUseCase.delete(id));
    }

//    4. Comment List Pagination (ADMIN)

    @GetMapping("/adm/comment-list")
    public ResponseEntity<ApiResponse<Page<CommentResponse>>> commentList(@RequestParam(defaultValue = "1") int page,
                                                                          @RequestParam(defaultValue = "5") int size) {
        return ResponseEntity.ok(commentUseCase.pagination(page - 1, size));
    }

//    5. Comment List By profileId(ADMIN)

    @GetMapping("/adm/comment-list/{profileId}")
    public ResponseEntity<ApiResponse<Page<CommentResponse>>> adminCommentList(@RequestParam(defaultValue = "1") int page,
                                                                               @RequestParam(defaultValue = "5") int size,
                                                                               @PathVariable("profileId") String profileId) {
        return ResponseEntity.ok(commentUseCase.paginationByProfileId(page - 1, size, profileId));
    }

//    6. Comment List By Profile (murojat qilgan odamning comment lari) (USER AND OWNER)

    @GetMapping("/comment-list")
    public ResponseEntity<ApiResponse<Page<CommentResponse>>> userCommentList(@RequestParam(defaultValue = "1") int page,
                                                                              @RequestParam(defaultValue = "5") int size) {
        return ResponseEntity.ok(commentUseCase.paginationByProfileId(page - 1, size, null));
    }

//    7. Comment List by videoId

    @GetMapping("/comment-list/{videoId}")
    public ResponseEntity<ApiResponse<Page<CommentResponse>>> commentList(@RequestParam(defaultValue = "1") int page,
                                                                          @RequestParam(defaultValue = "5") int size,
                                                                          @PathVariable("videoId") String videoId) {
        return ResponseEntity.ok(commentUseCase.paginationByVideoId(page - 1, size, videoId));
    }

//    8. Get Comment Replied Comment by comment Id (Commentga yozilgan commentlar)

    @GetMapping("/replied-comment/{commentId}")
    public ResponseEntity<ApiResponse<Page<CommentResponse>>> getRepliedComment(@RequestParam(defaultValue = "1") int page,
                                                                                @RequestParam(defaultValue = "5") int size,
                                                                                @PathVariable("commentId") String commentId) {
        return ResponseEntity.ok(commentUseCase.getRepliedComment(page - 1, size, commentId));
    }
}
