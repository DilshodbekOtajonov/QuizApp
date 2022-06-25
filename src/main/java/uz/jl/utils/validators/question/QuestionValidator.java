package uz.jl.utils.validators.question;

import uz.jl.configs.ApplicationContextHolder;
import uz.jl.dao.qa.QuestionDAO;
import uz.jl.exceptions.ValidationException;
import uz.jl.utils.validators.GenericValidator;
import uz.jl.vo.question.QuestionCreateVO;
import uz.jl.vo.question.QuestionUpdateVO;

import java.util.Objects;

public class QuestionValidator extends GenericValidator<QuestionCreateVO, QuestionUpdateVO, Long> {

    private static QuestionValidator instance;

    public static QuestionValidator getInstance() {
        if (instance == null) {
            instance = new QuestionValidator();
        }
        return instance;

    }


    QuestionDAO questionDAO = ApplicationContextHolder.getBean(QuestionDAO.class);

    @Override
    public void validateKey(Long id) throws ValidationException {

    }

    @Override
    public void validOnCreate(QuestionCreateVO vo) throws ValidationException {
        if (Objects.isNull(vo.getBody()) ) {
            throw new ValidationException("Question body can not be null");
        }
        if(Objects.isNull(vo.getAnswers()) ){
            throw new ValidationException("Answer list can not be null");
        }
        if(Objects.isNull(vo.getSubjectName())){
            throw new ValidationException("Subject name can not be null");
        }

    }


    @Override
    public void validOnUpdate(QuestionUpdateVO vo) throws ValidationException {

    }
}
