package uz.jl.utils.validators.student;

import uz.jl.exceptions.ValidationException;
import uz.jl.utils.validators.GenericValidator;
import uz.jl.vo.student.StudentCreateVO;
import uz.jl.vo.student.StudentUpdateVO;

import java.util.Objects;

public class StudentValidator extends GenericValidator<StudentCreateVO, StudentUpdateVO, Long> {


    private static StudentValidator instance;

    public static StudentValidator getInstance() {
        if (instance == null) {
            instance = new StudentValidator();
        }
        return instance;

    }

    @Override
    public void validateKey(Long id) throws ValidationException {

    }

    @Override
    public void validOnCreate(StudentCreateVO vo) throws ValidationException {
        existsByName(vo.getName());
        existsBySurname(vo.getSurname());
    }

    private void existsBySurname(String surname) {
        if (Objects.nonNull(surname)) {
            if (surname.isBlank()) {
                throw new ValidationException("The surname cannot be empty ");
            }
        } else
                throw new ValidationException("The surname cannot be null ");

    }


    private void existsByName(String name) {
        if (Objects.nonNull(name)) {
            if (name.isBlank()) {
                throw new ValidationException("The name cannot be empty ");

            }    }else throw new ValidationException("The name cannot be null");

    }

    @Override
    public void validOnUpdate(StudentUpdateVO vo) throws ValidationException {

    }
}
