package uz.jl.domains.users;

import jakarta.persistence.*;
import lombok.*;
import uz.jl.domains.auth.AuthUser;
import uz.jl.domains.subject.SubjectEntity;

import java.util.List;

/**
 * @author "Otajonov Dilshodbek
 * @since 6/24/22 3:39 PM (Friday)
 * QuizApp/IntelliJ IDEA
 */
@Entity
@Table(name = "teachers", schema = "auth")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@ToString
public class TeacherEntity {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String surname;
    @ManyToMany(targetEntity = SubjectEntity.class, cascade = CascadeType.MERGE,fetch = FetchType.EAGER)
    @JoinTable(name = "teacher_subject",
            joinColumns = @JoinColumn(name = "teacher_id"),
            inverseJoinColumns = @JoinColumn(name = "subject_id"),
            schema = "subject"
    )
    private List<SubjectEntity> subjectList;
    @OneToOne(targetEntity = AuthUser.class, cascade = CascadeType.MERGE)
    @JoinColumn(name = "user_id", nullable = false)
    private AuthUser user;
}
