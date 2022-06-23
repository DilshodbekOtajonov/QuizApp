package uz.jl.vo.answer;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.*;
import uz.jl.enums.AnswerStatus;
import uz.jl.vo.GenericVO;

/**
 * @author "Otajonov Dilshodbek
 * @since 6/22/22 6:52 PM (Wednesday)
 * QuizApp/IntelliJ IDEA
 */

@Getter
@Setter
public class AnswerVO extends GenericVO {
    private String body;

    private AnswerStatus status;

    @Builder(builderMethodName = "childBuilder")
    public AnswerVO(Long id, String body, AnswerStatus status) {
        super(id);
        this.body = body;
        this.status = status;
    }

    @Override
    public String toString() {
        return "AnswerVO{" +
                "body='" + body + '\'' +
                ", status=" + status +
                ", id=" + id +
                '}';
    }
}
