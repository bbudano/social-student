package hr.bernardbudano.socialstudent.model;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "post_like")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostLike {

    @EmbeddedId
    private PostLikeId id;

    @MapsId("postId")
    @ManyToOne
    private Post post;

    @MapsId("userId")
    @ManyToOne
    private UserData user;

}
