package hr.bernardbudano.socialstudent.dto.post;

import com.fasterxml.jackson.annotation.JsonProperty;
import hr.bernardbudano.socialstudent.model.Post;
import lombok.Data;
import org.joda.time.DateTime;

@Data
public class PostDto {

    @JsonProperty("id")
    private Long id;

    @JsonProperty("body")
    private String body;

    @JsonProperty("author")
    private String author;

    @JsonProperty("createdAt")
    private DateTime createdAt;

    @JsonProperty("likeCount")
    private int likeCount;

    @JsonProperty("commentCount")
    private int commentCount;

    public static PostDto fromEntity(Post post){
        PostDto postDto = new PostDto();
        postDto.setId(post.getId());
        postDto.setBody(post.getBody());
        postDto.setAuthor(post.getAuthor().getUsername());
        postDto.setCreatedAt(post.getPostedOn());
        postDto.setLikeCount(post.getLikes().size());
        postDto.setCommentCount(post.getComments().size());
        return postDto;
    }

}
