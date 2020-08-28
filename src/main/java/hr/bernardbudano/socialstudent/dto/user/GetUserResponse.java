package hr.bernardbudano.socialstudent.dto.user;

import hr.bernardbudano.socialstudent.dto.post.PostDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetUserResponse {

    private UserDto user;

    private List<PostDto> posts;

}
