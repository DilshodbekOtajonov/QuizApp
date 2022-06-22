package uz.jl.vo.answer;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import uz.jl.enums.AnswerStatus;
import uz.jl.vo.GenericVO;

/**
 * @author "Otajonov Dilshodbek
 * @since 6/22/22 6:52 PM (Wednesday)
 * QuizApp/IntelliJ IDEA
 */
public class AnswerVO extends GenericVO {
    private String body;

    private AnswerStatus status;

    public AnswerVO(Long id, String body, AnswerStatus status) {
        super(id);
        this.body = body;
        this.status = status;
    }
}
