package hr.bernardbudano.socialstudent.dto.post;

import com.fasterxml.jackson.annotation.JsonProperty;
import hr.bernardbudano.socialstudent.model.Post;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.joda.time.DateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostDto {

    private Long id;

    private String body;

    private String author;

    private DateTime postedOn;

    private int likeCount;

    private int commentCount;

    public static PostDto fromEntity(Post post){
        PostDto postDto = new PostDto();
        postDto.setId(post.getId());
        postDto.setBody(post.getBody());
        postDto.setAuthor(post.getAuthor().getUsername());
        postDto.setPostedOn(post.getPostedOn());
        postDto.setLikeCount(post.getLikes().size());
        postDto.setCommentCount(post.getComments().size());
        return postDto;
    }

}
