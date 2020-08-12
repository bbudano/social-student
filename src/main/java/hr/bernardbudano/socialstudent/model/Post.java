package hr.bernardbudano.socialstudent.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.joda.time.DateTime;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "post")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_data_id_seq_generator")
    @SequenceGenerator(name = "user_data_id_seq_generator", sequenceName = "user_data_id_seq", allocationSize = 10, initialValue = 10)
    @Column(name = "id", updatable = false)
    private Long id;

    @Column(name = "body")
    private String body;

    @Column(name = "posted_on")
    private DateTime postedOn = DateTime.now();

    @Column(name = "upvote_count")
    private int upvoteCount = 0;

    @Column(name = "downvote_count")
    private int downvoteCount = 0;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "author")
    private UserData author;

    @OneToMany(
            mappedBy = "post",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private Set<Comment> comments = new HashSet<>();

}
