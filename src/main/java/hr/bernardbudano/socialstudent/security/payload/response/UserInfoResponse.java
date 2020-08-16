package hr.bernardbudano.socialstudent.security.payload.response;

import hr.bernardbudano.socialstudent.dto.user.CredentialsDto;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserInfoResponse {

    private CredentialsDto credentials;

}
