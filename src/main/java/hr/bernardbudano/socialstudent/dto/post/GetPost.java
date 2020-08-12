package hr.bernardbudano.socialstudent.dto.post;

import hr.bernardbudano.socialstudent.dto.comment.CommentDto;
import hr.bernardbudano.socialstudent.model.Post;
import lombok.Data;
import org.joda.time.DateTime;
import org.joda.time.DateTimeComparator;

import java.util.Collections;
import java.util.List;

@Data
public class GetPost {

    private Long id;

    private String body;

    private String author;

    private DateTime postedOn;

    private int upvoteCount;

    private int downvoteCount;

    private List<CommentDto> comments;

    public static GetPost fromEntity(final Post post) {
        final GetPost postDto = new GetPost();

        postDto.setId(post.getId());
        postDto.setBody(post.getBody());
        postDto.setAuthor(post.getAuthor().getUsername());
        postDto.setPostedOn(post.getPostedOn());
        postDto.setUpvoteCount(post.getUpvoteCount());
        postDto.setDownvoteCount(post.getDownvoteCount());

        return postDto;
    }

}
