package uz.jl.vo.subject;

import lombok.Builder;
import uz.jl.vo.GenericVO;

/**
 * @author "Otajonov Dilshodbek
 * @since 6/22/22 6:29 PM (Wednesday)
 * QuizApp/IntelliJ IDEA
 */
public class SubjectUpdateVO extends GenericVO {
    private String title;

    @Builder(builderMethodName = "childBuilder")
    public SubjectUpdateVO(Long id, String title) {
        super(id);
        this.title = title;
    }
}
