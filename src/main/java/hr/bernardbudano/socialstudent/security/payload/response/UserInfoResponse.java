package hr.bernardbudano.socialstudent.security.payload.response;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class UserInfoResponse {

    private String username;
    private String email;
    private List<String> roles;

}
