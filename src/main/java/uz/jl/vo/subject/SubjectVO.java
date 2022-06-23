package uz.jl.vo.subject;

import lombok.*;
import uz.jl.vo.GenericVO;

/**
 * @author "Otajonov Dilshodbek
 * @since 6/22/22 6:29 PM (Wednesday)
 * QuizApp/IntelliJ IDEA
 */


@Getter
@Setter
public class SubjectVO extends GenericVO {
    private String title;

    @Builder(builderMethodName = "childBuilder")
    public SubjectVO(Long id, String title) {
        super(id);
        this.title = title;
    }
}
