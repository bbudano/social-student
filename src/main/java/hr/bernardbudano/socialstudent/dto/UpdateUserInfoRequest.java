package hr.bernardbudano.socialstudent.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.Optional;

@Data
public class UpdateUserInfoRequest {

    @JsonProperty("bio")
    private Optional<String> bio = Optional.empty();

    @JsonProperty("website")
    private Optional<String> website = Optional.empty();

    @JsonProperty("githubProfile")
    private Optional<String> githubProfile = Optional.empty();

    @JsonProperty("linkedinProfile")
    private Optional<String> linkedinProfile = Optional.empty();

}
