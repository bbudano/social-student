package hr.bernardbudano.socialstudent.dto.comment;

import hr.bernardbudano.socialstudent.model.Comment;
import lombok.Data;
import org.joda.time.DateTime;

@Data
public class CreateCommentResponse {

    private String body;

    private DateTime postedOn;

    private String author;

    private String authorAvatar;

    private Long postId;

    public static CreateCommentResponse fromEntity(Comment comment) {
        CreateCommentResponse response = new CreateCommentResponse();
        response.setBody(comment.getBody());
        response.setPostedOn(comment.getPostedOn());
        response.setAuthor(comment.getAuthor().getUsername());
        response.setAuthorAvatar(comment.getAuthor().getAvatarUrl());
        response.setPostId(comment.getPost().getId());
        return response;
    }

}
