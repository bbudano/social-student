package hr.bernardbudano.socialstudent.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "role")
@NoArgsConstructor
@Data
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "role_id_seq_generator")
    @SequenceGenerator(name = "role_id_seq_generator", sequenceName = "role_id_seq", allocationSize = 10, initialValue = 10)
    @Column(name = "id", updatable = false)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "name")
    private RoleName name;



}
