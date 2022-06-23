package uz.jl.vo.subject;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import uz.jl.vo.BaseVO;

import java.sql.Timestamp;

/**
 * @author "Otajonov Dilshodbek
 * @since 6/22/22 6:28 PM (Wednesday)
 * QuizApp/IntelliJ IDEA
 */

@Builder
@Getter
@Setter
@ToString
public class SubjectCreateVO implements BaseVO {
    private String title;
    private Timestamp createdAt;
    private Long createdBy;
}
