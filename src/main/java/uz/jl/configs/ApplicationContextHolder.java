package uz.jl.configs;

import uz.jl.dao.auth.AuthUserDAO;
import uz.jl.dao.qa.QuestionDAO;
import uz.jl.dao.subject.SubjectDAO;
import uz.jl.dao.variant.VariantDAO;
import uz.jl.service.QuestionService;
import uz.jl.service.SubjectService;
import uz.jl.service.VariantService;
import uz.jl.service.auth.AuthUserService;
import uz.jl.utils.BaseUtils;
import uz.jl.utils.validators.question.QuestionValidator;
import uz.jl.utils.validators.subject.SubjectValidator;
import uz.jl.utils.validators.authUser.AuthUserValidator;
import uz.jl.utils.validators.variantValidators.VariantValidator;

public class ApplicationContextHolder {

    @SuppressWarnings("unchecked")
    public static  <T> T getBean(String beanName) {
        return switch (beanName) {
            case "AuthUserDAO" -> (T) AuthUserDAO.getInstance();
            case "BaseUtils" -> (T) BaseUtils.getInstance();
            case "AuthUserService" -> (T) AuthUserService.getInstance();
            case "SubjectService" -> (T) SubjectService.getInstance();
            case "VariantService" -> (T) VariantService.getInstance();
            case "AuthUserValidator" -> (T) AuthUserValidator.getInstance();
            case "VariantValidator" -> (T) VariantValidator.getInstance();
            case "SubjectValidator" -> (T) SubjectValidator.getInstance();
            case "QuestionValidator" -> (T) QuestionValidator.getInstance();
            case "QuestionDAO" -> (T) QuestionDAO.getInstance();
            case "QuestionService" -> (T) QuestionService.getInstance();
            case "SubjectDAO" -> (T) SubjectDAO.getInstance();
            case "VariantDAO" -> (T) VariantDAO.getInstance();
            default -> throw new RuntimeException("Bean Not Found");
        };
    }

    public static  <T> T getBean(Class<T> clazz) {
        String beanName = clazz.getSimpleName();
        return getBean(beanName);
    }

}
