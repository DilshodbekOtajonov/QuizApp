package uz.jl.utils.validators.question;

import uz.jl.configs.ApplicationContextHolder;
import uz.jl.dao.qa.QuestionDAO;
import uz.jl.exceptions.ValidationException;
import uz.jl.utils.validators.GenericValidator;
import uz.jl.vo.question.QuestionCreateVO;
import uz.jl.vo.question.QuestionUpdateVO;

import java.util.Objects;

public class QuestionValidator extends GenericValidator<QuestionCreateVO, QuestionUpdateVO,Long> {

    private static QuestionValidator instance;

    QuestionDAO questionDAO= ApplicationContextHolder.getBean(QuestionDAO.class);

    @Override
    public void validateKey(Long id) throws ValidationException {
       existsById(id);
    }

    @Override
    public void validOnCreate(QuestionCreateVO vo) throws ValidationException {

    }

    @Override
    public void validOnUpdate(QuestionUpdateVO vo) throws ValidationException {

    }

    private void existsById(Long id){
        if(Objects.isNull(id) || Objects.isNull(questionDAO.findById(id)))
            throw new ValidationException("Question not fount by id");
    }

    public static QuestionValidator getInstance(){
        if (instance == null) {
            instance=new QuestionValidator();
        }
        return instance;
    }
}
