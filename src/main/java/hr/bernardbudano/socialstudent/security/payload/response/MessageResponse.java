package hr.bernardbudano.socialstudent.security.payload.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MessageResponse {

    private MessageType type;

    private String message;

}