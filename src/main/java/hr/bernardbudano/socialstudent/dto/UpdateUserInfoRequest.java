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

    @JsonProperty("linkedin_profile")
    private Optional<String> linkedinProfile = Optional.empty();

    @JsonProperty("facebook_profile")
    private Optional<String> facebookProfile = Optional.empty();

}
