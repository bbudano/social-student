package hr.bernardbudano.socialstudent.dto.post;

import lombok.Data;
import org.joda.time.DateTime;

@Data
public class CreatePostResponse {

    private Long id;

    private String body;

    private String author;

    private DateTime postedOn;

}
