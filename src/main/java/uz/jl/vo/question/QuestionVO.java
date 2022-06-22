package uz.jl.vo.question;

import lombok.Builder;
import uz.jl.domains.QA.AnswerEntity;
import uz.jl.enums.QuestionStatus;
import uz.jl.enums.Subject;
import uz.jl.vo.GenericVO;

import java.util.List;

/**
 * @author "Otajonov Dilshodbek
 * @since 6/22/22 6:22 PM (Wednesday)
 * QuizApp/IntelliJ IDEA
 */
public class QuestionVO extends GenericVO {

    private String body;
    private QuestionStatus status;
    private List<AnswerEntity> answers;
    private Subject subject;

    @Builder(builderMethodName = "childBuilder")
    public QuestionVO(Long id, String body, QuestionStatus status, List<AnswerEntity> answers, Subject subject) {
        super(id);
        this.body = body;
        this.status = status;
        this.answers = answers;
        this.subject = subject;
    }
}
