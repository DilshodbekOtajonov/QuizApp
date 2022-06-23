package uz.jl.domains.QA;

import jakarta.persistence.*;
import lombok.*;
import uz.jl.domains.Auditable;
import uz.jl.domains.SubjectEntity;
import uz.jl.enums.QuestionStatus;

import java.sql.Timestamp;
import java.util.List;


@Entity
@Getter
@ToString
@Setter
@Table(name = "questions", schema = "question")
@NoArgsConstructor
public class QuestionEntity extends Auditable {

    private String body;
    @Enumerated(EnumType.STRING)
    private QuestionStatus status;
    @OneToMany(cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    @JoinColumn(name = "question_id")
    private List<AnswerEntity> answers;

    @OneToOne(cascade = CascadeType.ALL, targetEntity = SubjectEntity.class)
    @JoinColumn(name = "subject_id")
    private SubjectEntity subject;

    @Builder(builderMethodName = "childBuilder")
    public QuestionEntity(long id, Timestamp createdAt, Long createdBy, Timestamp updatedAt, Long updatedBy, Boolean deleted, String body, QuestionStatus status, List<AnswerEntity> answers, SubjectEntity subject) {
        super(id, createdAt, createdBy, updatedAt, updatedBy, deleted);
        this.body = body;
        this.status = status;
        this.answers = answers;
        this.subject = subject;
    }
}
