package hr.bernardbudano.socialstudent.security.payload.request;

import lombok.Data;

import java.util.Set;

import javax.validation.constraints.*;

@Data
public class SignupRequest {

    private String username;

    private String email;

    private Set<String> roles;

    private String password;

    private String confirmPassword;
}
