package hr.bernardbudano.socialstudent.dto.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.joda.time.DateTime;

import java.util.List;

@Data
@AllArgsConstructor
public class CredentialsDto {

    private String username;
    private String email;
    private String bio;
    private List<String> roles;
    private DateTime joinedOn;
    private String website;
    private String githubProfile;
    private String linkedinProfile;

}
