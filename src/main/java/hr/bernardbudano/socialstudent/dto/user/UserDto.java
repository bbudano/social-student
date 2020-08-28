package hr.bernardbudano.socialstudent.dto.user;

import hr.bernardbudano.socialstudent.model.UserData;
import lombok.Data;
import org.joda.time.DateTime;

@Data
public class UserDto {

    private Long id;

    private String username;

    private String bio;

    private String email;

    private String website;

    private String githubProfile;

    private String linkedinProfile;

    private DateTime joinedOn;

    public static UserDto fromEntity(UserData user) {
        UserDto userDto = new UserDto();
        userDto.setId(user.getId());
        userDto.setUsername(user.getUsername());
        userDto.setBio(user.getBio());
        userDto.setEmail(user.getEmail());
        userDto.setWebsite(user.getWebsite());
        userDto.setGithubProfile(user.getGithubProfile());
        userDto.setLinkedinProfile(user.getLinkedinProfile());
        userDto.setJoinedOn(user.getJoinedOn());
        return userDto;
    }

}
