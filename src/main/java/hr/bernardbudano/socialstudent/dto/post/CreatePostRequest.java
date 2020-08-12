package hr.bernardbudano.socialstudent.dto.post;

import com.fasterxml.jackson.annotation.JsonProperty;
import hr.bernardbudano.socialstudent.model.Post;
import hr.bernardbudano.socialstudent.model.UserData;
import lombok.Data;

@Data
public class CreatePostRequest {

    @JsonProperty("body")
    private String body;

    public static Post toEntity(CreatePostRequest request, UserData author) {
        final Post post = new Post();
        post.setBody(request.getBody());
        post.setAuthor(author);
        return post;
    }

}
