package uz.jl.vo.variant;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import uz.jl.domains.QA.QuestionEntity;
import uz.jl.domains.auth.AuthUser;
import uz.jl.enums.QuestionStatus;
import uz.jl.vo.GenericVO;
import uz.jl.vo.question.QuestionVO;

import java.sql.Timestamp;
import java.util.List;

/**
 * @author "Otajonov Dilshodbek
 * @since 6/23/22 8:53 AM (Thursday)
 * QuizApp/IntelliJ IDEA
 */

@Getter
@Setter
public class VariantVO extends GenericVO {
    private AuthUser user;
    private QuestionStatus status;
    private List<QuestionVO> questions;
    private Integer numberOfRightAnswers;

    private Timestamp createdAt;

    @Builder(builderMethodName = "childBuilder")
    public VariantVO(Long id, AuthUser user, QuestionStatus status, List<QuestionVO> questions, Integer numberOfRightAnswers, Timestamp createdAt) {
        super(id);
        this.user = user;
        this.status = status;
        this.questions = questions;
        this.numberOfRightAnswers = numberOfRightAnswers;
        this.createdAt = createdAt;
    }
}
