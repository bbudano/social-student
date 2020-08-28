package hr.bernardbudano.socialstudent.security.payload.request;

import lombok.Data;

import java.util.Set;

import javax.validation.constraints.*;

@Data
public class SignupRequest {

    @NotBlank(message = "Username must not be blank")
    @Size(min = 3, max = 20)
    private String username;

    @NotBlank(message = "Email must not be blank")
    @Size(max = 50)
    @Email
    private String email;

    private Set<String> roles;

    @NotBlank(message = "Password must not be blank")
    @Size(min = 6, max = 40, message = "Password must be at least 6 characters long")
    private String password;
}
