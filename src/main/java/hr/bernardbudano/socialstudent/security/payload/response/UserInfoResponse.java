package hr.bernardbudano.socialstudent.security.payload.response;

import hr.bernardbudano.socialstudent.dto.PostLikeDto;
import hr.bernardbudano.socialstudent.dto.user.CredentialsDto;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class UserInfoResponse {

    private CredentialsDto credentials;

    private List<String> roles;

    private List<PostLikeDto> likes;

}
