package uz.jl.vo.teacher;

import lombok.*;
import uz.jl.domains.subject.SubjectEntity;
import uz.jl.vo.GenericVO;
import uz.jl.vo.subject.SubjectVO;

import java.util.List;

@Getter
@Setter
@ToString
public class TeacherVO extends GenericVO {

    private String name;
    private String surname;
    private List<SubjectVO> subjectList;

    @Builder
    public TeacherVO(Long id, String name, String surname, List<SubjectVO> subjectList) {
        super(id);
        this.name = name;
        this.surname = surname;
        this.subjectList = subjectList;
    }
}
