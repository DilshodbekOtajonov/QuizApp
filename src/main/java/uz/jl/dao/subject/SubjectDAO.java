package uz.jl.dao.subject;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import uz.jl.dao.GenericDAO;
import uz.jl.domains.SubjectEntity;

/**
 * @author "Otajonov Dilshodbek
 * @since 6/23/22 7:50 AM (Thursday)
 * QuizApp/IntelliJ IDEA
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class SubjectDAO extends GenericDAO<SubjectEntity, Long> {
    private static SubjectDAO instance;

    public static SubjectDAO getInstance() {
        if (instance == null) {
            instance = new SubjectDAO();
        }
        return instance;
    }
}
