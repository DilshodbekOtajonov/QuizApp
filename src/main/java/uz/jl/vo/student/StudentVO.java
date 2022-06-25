package uz.jl.vo.student;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import uz.jl.domains.auth.AuthUser;
import uz.jl.enums.QuestionStatus;
import uz.jl.vo.GenericVO;
import uz.jl.vo.answer.AnswerVO;
import uz.jl.vo.subject.SubjectVO;

import java.util.List;

@Setter
@Getter
public class StudentVO  extends GenericVO {
    private String name;
    private String surname;
    private AuthUser user;


    public StudentVO(Long id) {
        super(id);
    }
}
