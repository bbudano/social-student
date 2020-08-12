package hr.bernardbudano.socialstudent.model;

import lombok.Data;
import org.joda.time.DateTime;

import javax.persistence.*;

@Entity
@Table(name = "comment")
@Data
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "comment_id_seq_generator")
    @SequenceGenerator(name = "comment_id_seq_generator", sequenceName = "comment_id_seq", allocationSize = 10, initialValue = 10)
    @Column(name = "id", updatable = false)
    private Long id;

    @Column(name = "body")
    private String body;

    @Column(name = "posted_on")
    private DateTime postedOn = DateTime.now();

    @ManyToOne(fetch = FetchType.LAZY)
    private UserData author;

    @ManyToOne(fetch = FetchType.LAZY)
    private Post post;

}
