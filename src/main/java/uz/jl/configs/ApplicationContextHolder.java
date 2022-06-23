package uz.jl.configs;

import uz.jl.dao.auth.AuthUserDAO;
import uz.jl.dao.qa.QuestionDAO;
import uz.jl.dao.subject.SubjectDAO;
import uz.jl.service.QuestionService;
import uz.jl.service.auth.AuthUserService;
import uz.jl.utils.BaseUtils;
import uz.jl.utils.validators.authUser.AuthUserValidator;

public class ApplicationContextHolder {

    @SuppressWarnings("unchecked")
    public static  <T> T getBean(String beanName) {
        return switch (beanName) {
            case "AuthUserDAO" -> (T) AuthUserDAO.getInstance();
            case "BaseUtils" -> (T) BaseUtils.getInstance();
            case "AuthUserService" -> (T) AuthUserService.getInstance();
            case "AuthUserValidator" -> (T) AuthUserValidator.getInstance();
            case "QuestionDAO" -> (T) QuestionDAO.getInstance();
            case "QuestionService" -> (T) QuestionService.getInstance();
            case "SubjectDAO" -> (T) SubjectDAO.getInstance();
            default -> throw new RuntimeException("Bean Not Found");
        };
    }

    public static  <T> T getBean(Class<T> clazz) {
        String beanName = clazz.getSimpleName();
        return getBean(beanName);
    }

}
