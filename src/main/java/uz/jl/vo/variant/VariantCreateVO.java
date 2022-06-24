package uz.jl.vo.variant;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import uz.jl.domains.QA.QuestionEntity;
import uz.jl.domains.auth.AuthUser;
import uz.jl.enums.QuestionStatus;
import uz.jl.vo.BaseVO;
import uz.jl.vo.question.QuestionVO;

import java.util.List;

/**
 * @author "Otajonov Dilshodbek
 * @since 6/23/22 8:53 AM (Thursday)
 * QuizApp/IntelliJ IDEA
 */

@Builder
@Getter
@Setter
public class VariantCreateVO implements BaseVO {
    private String subjectName;
    private QuestionStatus level;
    private Integer numberOfQuestions;
    private Long userId;
}
