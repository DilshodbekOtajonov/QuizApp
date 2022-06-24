package uz.jl.domains.auth;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
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
public class TeacherEntity {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String username;
    private String surname;
    @ManyToMany(targetEntity = SubjectEntity.class, cascade = CascadeType.MERGE)
    @JoinTable(name = "teacher_subject",
            joinColumns = @JoinColumn(name = "teacher_id"),
            inverseJoinColumns = @JoinColumn(name = "subject_id"),
            schema = "subject"
    )
    private List<StudentEntity> subjectList;
    @OneToOne(targetEntity = AuthUser.class, cascade = CascadeType.MERGE)
    @JoinColumn(name = "user_id", nullable = false)
    private AuthUser user;
}
