package hr.bernardbudano.socialstudent.security.payload.response;

import lombok.Data;

@Data
public class JwtResponse {
    private String accessToken;

    public JwtResponse(String accessToken) {
        this.accessToken = accessToken;
    }
}
