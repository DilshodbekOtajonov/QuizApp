package uz.jl.vo.question;

import lombok.Builder;
import uz.jl.enums.QuestionStatus;
import uz.jl.vo.GenericVO;
import uz.jl.vo.answer.AnswerCreateVO;

import java.util.List;

/**
 * @author "Otajonov Dilshodbek
 * @since 6/22/22 6:23 PM (Wednesday)
 * QuizApp/IntelliJ IDEA
 */

public class QuestionUpdateVO extends GenericVO {


    public String body;

    public QuestionStatus status;
    private List<AnswerCreateVO> answers;


    public QuestionUpdateVO(Long id, List<AnswerCreateVO> answers) {
        super(id);

    }

    @Builder(builderMethodName = "childBuilder")

    public QuestionUpdateVO(Long id, String body, QuestionStatus status, List<AnswerCreateVO> answers) {
        super(id);
        this.body = body;
        this.status = status;
        this.answers = answers;
    }
}
