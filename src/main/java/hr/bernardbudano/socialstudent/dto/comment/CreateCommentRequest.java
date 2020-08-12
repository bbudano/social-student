package hr.bernardbudano.socialstudent.dto.comment;

import com.fasterxml.jackson.annotation.JsonProperty;
import hr.bernardbudano.socialstudent.model.Comment;
import hr.bernardbudano.socialstudent.model.Post;
import hr.bernardbudano.socialstudent.model.UserData;
import lombok.Data;

@Data
public class CreateCommentRequest {

    @JsonProperty("body")
    private String body;

    public static Comment toEntity(CreateCommentRequest request, UserData author, Post post) {
        Comment comment = new Comment();
        comment.setBody(request.getBody());
        comment.setAuthor(author);
        comment.setPost(post);
        return comment;
    }

}
