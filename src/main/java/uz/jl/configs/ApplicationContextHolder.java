package uz.jl.configs;

import uz.jl.dao.student.StudentDAO;
import uz.jl.dao.auth.AuthUserDAO;
import uz.jl.dao.qa.QuestionDAO;
import uz.jl.dao.subject.SubjectDAO;
import uz.jl.dao.teacher.TeacherDAO;
import uz.jl.dao.variant.VariantDAO;
import uz.jl.service.*;
import uz.jl.service.auth.AuthUserService;
import uz.jl.utils.BaseUtils;
import uz.jl.utils.validators.question.QuestionValidator;
import uz.jl.utils.validators.student.StudentValidator;
import uz.jl.utils.validators.subject.SubjectValidator;
import uz.jl.utils.validators.authUser.AuthUserValidator;
import uz.jl.utils.validators.teacher.TeacherValidator;
import uz.jl.utils.validators.variantValidators.VariantValidator;

public class ApplicationContextHolder {

    @SuppressWarnings("unchecked")
    public static <T> T getBean(String beanName) {
        return switch (beanName) {
            case "BaseUtils" -> (T) BaseUtils.getInstance();
            case "AuthUserService" -> (T) AuthUserService.getInstance();
            case "StudentService" -> (T) StudentService.getInstance();
            case "SubjectService" -> (T) SubjectService.getInstance();
            case "VariantService" -> (T) VariantService.getInstance();
            case "TeacherService" -> (T) TeacherService.getInstance();
            case "QuestionService" -> (T) QuestionService.getInstance();
            case "AuthUserValidator" -> (T) AuthUserValidator.getInstance();
            case "StudentValidator" -> (T) StudentValidator.getInstance();
            case "VariantValidator" -> (T) VariantValidator.getInstance();
            case "SubjectValidator" -> (T) SubjectValidator.getInstance();
            case "QuestionValidator" -> (T) QuestionValidator.getInstance();
            case "TeacherValidator" -> (T) TeacherValidator.getInstance();
            case "QuestionDAO" -> (T) QuestionDAO.getInstance();
            case "AuthUserDAO" -> (T) AuthUserDAO.getInstance();
            case "SubjectDAO" -> (T) SubjectDAO.getInstance();
            case "VariantDAO" -> (T) VariantDAO.getInstance();
            case "StudentDAO" -> (T) StudentDAO.getInstance();
            case "TeacherDAO" -> (T) TeacherDAO.getInstance();
            default -> throw new RuntimeException("Bean Not Found");
        };
    }

    public static <T> T getBean(Class<T> clazz) {
        String beanName = clazz.getSimpleName();
        return getBean(beanName);
    }

}
