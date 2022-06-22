package uz.jl.utils.validators;

import uz.jl.exceptions.ValidationException;
import uz.jl.vo.BaseVO;
import uz.jl.vo.GenericVO;

import java.io.Serializable;

/**
 * @author "Otajonov Dilshodbek
 * @since 6/22/22 7:20 AM (Wednesday)
 * jira/IntelliJ IDEA
 */
public abstract class GenericValidator<CreateVO extends BaseVO, UpdateVO extends GenericVO, K extends Serializable> implements BaseValidator {

    public abstract void validateKey(K id) throws ValidationException;

    public abstract void validOnCreate(CreateVO vo) throws ValidationException;

    public abstract void validOnUpdate(UpdateVO vo) throws ValidationException;
}
