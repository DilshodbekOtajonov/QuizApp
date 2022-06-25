package uz.jl.vo.student;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import uz.jl.domains.auth.AuthUser;
import uz.jl.enums.QuestionStatus;
import uz.jl.vo.BaseVO;
import uz.jl.vo.answer.AnswerCreateVO;

import java.util.List;
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class StudentCreateVO  implements BaseVO {

    private String name;
    private String surname;
    private AuthUser user;

}
