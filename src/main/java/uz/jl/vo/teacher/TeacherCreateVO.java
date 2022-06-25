package uz.jl.vo.teacher;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import uz.jl.domains.auth.AuthUser;
import uz.jl.domains.subject.SubjectEntity;
import uz.jl.vo.BaseVO;

import javax.security.auth.Subject;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class TeacherCreateVO implements BaseVO {
    private String name;
    private String surname;
    private AuthUser user;
    private List<SubjectEntity> subjectList;
}
