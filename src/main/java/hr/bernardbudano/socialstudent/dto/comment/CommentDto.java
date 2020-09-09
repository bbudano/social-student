package hr.bernardbudano.socialstudent.dto.comment;

import hr.bernardbudano.socialstudent.model.Comment;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.joda.time.DateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommentDto {

    private Long id;

    private String body;

    private DateTime postedOn;

    private String author;

    private String authorAvatar;

    private Long postId;

    public static CommentDto fromEntity(Comment comment) {
        CommentDto commentDto = new CommentDto();
        commentDto.setId(comment.getId());
        commentDto.setBody(comment.getBody());
        commentDto.setPostedOn(comment.getPostedOn());
        commentDto.setAuthor(comment.getAuthor().getUsername());
        commentDto.setAuthorAvatar(comment.getAuthor().getAvatarUrl());
        commentDto.setPostId(comment.getPost().getId());
        return commentDto;
    }

}
