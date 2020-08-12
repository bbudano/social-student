package hr.bernardbudano.socialstudent.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.joda.time.DateTime;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "user_data")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class UserData {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_data_id_seq_generator")
    @SequenceGenerator(name = "user_data_id_seq_generator", sequenceName = "user_data_id_seq", allocationSize = 10, initialValue = 10)
    @Column(name = "id", updatable = false)
    private Long id;

    @Column(name = "username")
    private String username;

    @Column(name = "email")
    private String email;

    @Column(name = "password")
    private String password;

    @Column(name = "bio")
    private String bio;

    @Column(name = "website")
    private String website;

    @Column(name = "linkedin_profile")
    private String linkedinProfile;

    @Column(name = "facebook_profile")
    private String facebookProfile;

    @Column(name = "joined_on")
    private DateTime joinedOn = DateTime.now();

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "user_role",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles = new HashSet<>();

    @OneToMany(
            mappedBy = "author",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    @ToString.Exclude
    private List<Post> posts = new ArrayList<>();

    @OneToMany(
            mappedBy = "author",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private Set<Comment> comments = new HashSet<>();

    public UserData(String username, String email, String password) {
        this.username = username;
        this.email = email;
        this.password = password;
    }

}
