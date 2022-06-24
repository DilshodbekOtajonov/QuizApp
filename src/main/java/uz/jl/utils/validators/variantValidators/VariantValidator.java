package uz.jl.utils.validators.variantValidators;

import uz.jl.configs.ApplicationContextHolder;
import uz.jl.dao.auth.AuthUserDAO;
import uz.jl.dao.subject.SubjectDAO;
import uz.jl.dao.variant.VariantDAO;
import uz.jl.enums.QuestionStatus;
import uz.jl.exceptions.ValidationException;
import uz.jl.utils.validators.GenericValidator;
import uz.jl.utils.validators.authUser.AuthUserValidator;
import uz.jl.vo.variant.VariantCreateVO;
import uz.jl.vo.variant.VariantUpdateVO;

import java.util.Arrays;
import java.util.Objects;

/**
 * @author "Otajonov Dilshodbek
 * @since 6/24/22 5:56 PM (Friday)
 * QuizApp/IntelliJ IDEA
 */
public class VariantValidator extends GenericValidator<VariantCreateVO, VariantUpdateVO, Long> {
    private static VariantValidator instance;
    VariantDAO variantDAO = ApplicationContextHolder.getBean(VariantDAO.class);
    AuthUserDAO authUserDAO = ApplicationContextHolder.getBean(AuthUserDAO.class);
    SubjectDAO subjectDAO = ApplicationContextHolder.getBean(SubjectDAO.class);

    @Override
    public void validateKey(Long id) throws ValidationException {
        existsById(id);
    }

    @Override
    public void validOnCreate(VariantCreateVO vo) throws ValidationException {
        if (vo.getUserId() == null || authUserDAO.findById(vo.getUserId()) == null)
            throw new ValidationException("User not found");
        if (vo.getLevel() == null || !Arrays.asList(QuestionStatus.values()).contains(vo.getLevel()))
            throw new ValidationException("Level not found");
        if (vo.getNumberOfQuestions() == null || vo.getNumberOfQuestions() < 0 || vo.getNumberOfQuestions() > 100)
            throw new ValidationException("Invalid number of questions");
        if (vo.getSubjectName() == null || subjectDAO.findByName(vo.getSubjectName()) == null)
            throw new ValidationException("Invalid subject name");


    }

    @Override
    public void validOnUpdate(VariantUpdateVO vo) throws ValidationException {

    }

    private void existsById(Long id) {
        if (Objects.isNull(id) || Objects.isNull(variantDAO.findById(id)))
            throw new ValidationException("Variant not found by id");
    }

    public static VariantValidator getInstance() {
        if (instance == null) {
            instance = new VariantValidator();
        }
        return instance;
    }
}
