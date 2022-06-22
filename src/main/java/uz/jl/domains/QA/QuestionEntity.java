package uz.jl.domains.QA;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.NoArgsConstructor;
import uz.jl.domains.Auditable;
import uz.jl.domains.SubjectEntity;
import uz.jl.enums.QuestionStatus;
import uz.jl.enums.Subject;

import java.sql.Timestamp;
import java.util.List;

/**
 * @author "Otajonov Dilshodbek
 * @since 6/22/22 2:14 PM (Wednesday)
 * QuizApp/IntelliJ IDEA
 */

@Entity
@Table(name = "questions", schema = "question")
@NoArgsConstructor
public class QuestionEntity extends Auditable {

    private String body;
    @Enumerated(EnumType.STRING)
    private QuestionStatus status;
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "question_id")
    private List<AnswerEntity> answers;

    @OneToOne(cascade = CascadeType.ALL, targetEntity = SubjectEntity.class)
    @JoinColumn(name = "subject_id")
    private SubjectEntity subject;

    @Builder(builderMethodName = "childBuilder")
    public QuestionEntity(Long id, Timestamp createdAt, Long createdBy, Timestamp updatedAt, Long updatedBy, Boolean deleted, String body, List<AnswerEntity> answers) {
        super(id, createdAt, createdBy, updatedAt, updatedBy, deleted);
        this.body = body;
        this.answers = answers;
    }

}
