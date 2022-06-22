package uz.jl.dao;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import uz.jl.dao.auth.AuthUserDAO;
import uz.jl.domains.QuestionEntity;

/**
 * @author "Otajonov Dilshodbek
 * @since 6/22/22 2:19 PM (Wednesday)
 * QuizApp/IntelliJ IDEA
 */

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class QuestionDAO extends GenericDAO<QuestionEntity, Long> {
    private static QuestionDAO instance;


    public static QuestionDAO getInstance() {
        if (instance == null) {
            instance = new QuestionDAO();
        }
        return instance;
    }
}
