package hr.bernardbudano.socialstudent.dto.user;

import com.fasterxml.jackson.annotation.JsonProperty;
import hr.bernardbudano.socialstudent.dto.post.PostDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetUserProfileResponse {

    @JsonProperty("user")
    private UserDto user;

    @JsonProperty("isAdmin")
    private boolean isAdmin;

    @JsonProperty("posts")
    private List<PostDto> posts;

}
