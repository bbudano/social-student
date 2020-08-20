package hr.bernardbudano.socialstudent.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostLikeDto {

    private String username;

    private Long postId;

}
