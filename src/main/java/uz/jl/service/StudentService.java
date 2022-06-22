package uz.jl.service;

import uz.jl.configs.ApplicationContextHolder;
import uz.jl.dao.AbstractDAO;
import uz.jl.dao.qa.QuestionDAO;
import uz.jl.utils.BaseUtils;

/**
 * @author "Otajonov Dilshodbek
 * @since 6/22/22 4:48 PM (Wednesday)
 * QuizApp/IntelliJ IDEA
 */


public class StudentService extends AbstractDAO<QuestionDAO> {
    private static StudentService instance;

    public StudentService() {
        super(
                ApplicationContextHolder.getBean(QuestionDAO.class),
                ApplicationContextHolder.getBean(BaseUtils.class)
        );
    }




    public static StudentService getInstance() {

        if (instance == null) {
            instance = new StudentService();
        }
        return instance;
    }
}
