package uz.jl.vo.variant;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import uz.jl.domains.auth.AuthUser;
import uz.jl.enums.QuestionStatus;
import uz.jl.vo.GenericVO;
import uz.jl.vo.question.QuestionVO;

import java.sql.Timestamp;
import java.util.List;

@Getter
@ToString
@Setter
public class VariantVO extends GenericVO {
    private Timestamp createdAt;
    private QuestionStatus status;
    private List<QuestionVO> questions;
    private Integer numberOfRightAnswers;

    @Builder(builderMethodName = "childBuilder")
    public VariantVO(Long id,Timestamp createdAt,QuestionStatus status, List<QuestionVO> questions, Integer numberOfRightAnswers) {
        super(id);
        this.createdAt=createdAt;
        this.status = status;
        this.questions = questions;
        this.numberOfRightAnswers = numberOfRightAnswers;
    }
}
