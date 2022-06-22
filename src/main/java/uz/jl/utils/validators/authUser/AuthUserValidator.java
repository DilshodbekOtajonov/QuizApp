package uz.jl.utils.validators.authUser;

import uz.jl.configs.ApplicationContextHolder;
import uz.jl.dao.auth.AuthUserDAO;
import uz.jl.domains.auth.AuthUser;
import uz.jl.exceptions.ValidationException;
import uz.jl.utils.validators.GenericValidator;
import uz.jl.vo.auth.AuthUserCreateVO;
import uz.jl.vo.auth.AuthUserUpdateVO;

import java.util.Objects;

/**
 * @author "Otajonov Dilshodbek
 * @since 6/22/22 7:35 AM (Wednesday)
 * jira/IntelliJ IDEA
 */
public class AuthUserValidator extends GenericValidator<AuthUserCreateVO, AuthUserUpdateVO, Long> {

    private static AuthUserValidator instance;
    AuthUserDAO authUserDAO = ApplicationContextHolder.getBean(AuthUserDAO.class);

    @Override
    public void validateKey(Long id) throws ValidationException {
        existsById(id);
    }

    @Override
    public void validOnCreate(AuthUserCreateVO vo) throws ValidationException {
        existsByUsername(vo.getUsername());
        existsByEmail(vo.getEmail());
    }


    @Override
    public void validOnUpdate(AuthUserUpdateVO vo) throws ValidationException {

    }

    private void existsByUsername(String username) {
        if (Objects.nonNull(username)) {
            if (authUserDAO.findByUserName(username).isPresent())
                throw new ValidationException("user already exists by username");
        }
    }

    private void existsByEmail(String email) {
        if (Objects.nonNull(email)) {
            if (authUserDAO.findByEmail(email).isPresent())
                throw new ValidationException("User already exists by email");
        }
    }

    private void existsById(Long id) {
        if (Objects.isNull(id) || Objects.isNull(authUserDAO.findById(id)))
            throw new ValidationException("User not found by id");
    }


    public static AuthUserValidator getInstance() {
        if (instance == null) {
            instance = new AuthUserValidator();
        }
        return instance;
    }
}
