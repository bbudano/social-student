package hr.bernardbudano.socialstudent.model;

import lombok.*;
import org.joda.time.DateTime;

import javax.persistence.*;
import java.util.*;

@Entity
@Table(name = "post")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "post_id_seq_generator")
    @SequenceGenerator(name = "post_id_seq_generator", sequenceName = "post_id_seq", allocationSize = 10, initialValue = 10)
    @Column(name = "id", updatable = false)
    private Long id;

    @Column(columnDefinition = "TEXT", name = "body", nullable = false)
    private String body;

    @Column(name = "posted_on", nullable = false)
    private DateTime postedOn = DateTime.now();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "author", nullable = false)
    private UserData author;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @OneToMany(
            mappedBy = "post",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<Comment> comments = new ArrayList<>();

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @OneToMany(
            mappedBy = "post",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<PostLike> likes = new ArrayList<>();

    public void removeComment(final Iterator i, Comment comment) {
        comment.setPost(null);
        i.remove();
    }

    public void removeLike(final Iterator i, PostLike postLike) {
        postLike.setPost(null);
        i.remove();
    }

}
