package uz.jl.vo.answer;

import lombok.*;
import uz.jl.enums.AnswerStatus;
import uz.jl.vo.BaseVO;

/**
 * @author "Otajonov Dilshodbek
 * @since 6/22/22 6:52 PM (Wednesday)
 * QuizApp/IntelliJ IDEA
 */

@Builder
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class AnswerCreateVO implements BaseVO {

    private String body;

    private AnswerStatus status;
}
