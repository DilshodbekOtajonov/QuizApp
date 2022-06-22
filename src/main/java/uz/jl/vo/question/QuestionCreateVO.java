package uz.jl.vo.question;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import uz.jl.domains.QA.AnswerEntity;
import uz.jl.enums.QuestionStatus;
import uz.jl.enums.Subject;
import uz.jl.vo.BaseVO;
import uz.jl.vo.answer.AnswerCreateVO;

import java.util.List;

/**
 * @author "Otajonov Dilshodbek
 * @since 6/22/22 6:22 PM (Wednesday)
 * QuizApp/IntelliJ IDEA
 */

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class QuestionCreateVO implements BaseVO {

    private String body;
    private QuestionStatus status;
    private List<AnswerCreateVO> answers;
    private Subject subject;
}
