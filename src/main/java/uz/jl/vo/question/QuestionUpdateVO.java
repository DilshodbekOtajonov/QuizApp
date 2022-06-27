package uz.jl.vo.question;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import uz.jl.enums.QuestionStatus;
import uz.jl.vo.GenericVO;
import uz.jl.vo.answer.AnswerVO;
import uz.jl.vo.subject.SubjectVO;

import java.util.List;

/**
 * @author "Otajonov Dilshodbek
 * @since 6/22/22 6:23 PM (Wednesday)
 * QuizApp/IntelliJ IDEA
 */

@Getter
@Setter
public class QuestionUpdateVO extends GenericVO {
    private String body;
    private QuestionStatus status;
    private List<AnswerVO> answers;
    private SubjectVO subject;

    @Builder(builderMethodName = "childBuilder")
    public QuestionUpdateVO(Long id, String body, QuestionStatus status, List<AnswerVO> answers, SubjectVO subject) {
        super(id);
        this.body = body;
        this.status = status;
        this.answers = answers;
        this.subject = subject;
    }
}
