package uz.jl.vo.question;

import jakarta.persistence.*;
import uz.jl.domains.QA.AnswerEntity;
import uz.jl.enums.QuestionStatus;
import uz.jl.enums.Subject;
import uz.jl.vo.BaseVO;

import java.util.List;

/**
 * @author "Otajonov Dilshodbek
 * @since 6/22/22 6:22 PM (Wednesday)
 * QuizApp/IntelliJ IDEA
 */
public class QuestionCreateVO implements BaseVO {

    private String body;
    private QuestionStatus status;
    private List<AnswerEntity> answers;

    private Subject subject;
}
