package org.company.youtube.dto.record.comment;


public record CommentRequest(

        String profileId,
        String videoId,
        String content,
        String replyId
) {

    public record CommentUpdate(
            String content
    ){
    }
}
