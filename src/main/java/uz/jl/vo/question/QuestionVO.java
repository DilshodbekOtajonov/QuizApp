package uz.jl.vo.question;

import jakarta.persistence.*;
import lombok.*;
import uz.jl.domains.QA.AnswerEntity;
import uz.jl.domains.SubjectEntity;
import uz.jl.enums.QuestionStatus;
import uz.jl.vo.GenericVO;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@ToString
public class QuestionVO extends GenericVO{
    private String body;

    @Enumerated(EnumType.STRING)
    private QuestionStatus status;
    private LocalDateTime createdAt;
    private List<AnswerEntity> answers;
    private SubjectEntity subject;

    @Builder(builderMethodName = "childBuilder")
    public QuestionVO(Long id, String body, QuestionStatus status, LocalDateTime createdAt, List<AnswerEntity> answers, SubjectEntity subject) {
        super(id);
        this.body = body;
        this.status = status;
        this.createdAt = createdAt;
        this.answers = answers;
        this.subject = subject;
    }
}
