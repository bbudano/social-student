package hr.bernardbudano.socialstudent.model;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "post_like")
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
