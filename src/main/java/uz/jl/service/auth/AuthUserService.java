package uz.jl.service.auth;

import jakarta.transaction.Transactional;
import lombok.NonNull;
import uz.jl.configs.ApplicationContextHolder;
import uz.jl.dao.AbstractDAO;
import uz.jl.dao.auth.AuthUserDAO;
import uz.jl.domains.auth.AuthUser;
import uz.jl.enums.AuthRole;
import uz.jl.service.GenericCRUDService;
import uz.jl.utils.BaseUtils;
import uz.jl.utils.validators.authUser.AuthUserValidator;
import uz.jl.vo.auth.AuthUserCreateVO;
import uz.jl.vo.auth.AuthUserUpdateVO;
import uz.jl.vo.auth.AuthUserVO;
import uz.jl.vo.auth.Session;
import uz.jl.vo.http.AppErrorVO;
import uz.jl.vo.http.DataVO;
import uz.jl.vo.http.Response;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class AuthUserService extends AbstractDAO<AuthUserDAO> implements GenericCRUDService<
        AuthUserVO,
        AuthUserCreateVO,
        AuthUserUpdateVO,
        Long> {

    private static AuthUserService instance;
    private final AuthUserValidator validator = ApplicationContextHolder.getBean(AuthUserValidator.class);

    private AuthUserService() {
        super(
                ApplicationContextHolder.getBean(AuthUserDAO.class),
                ApplicationContextHolder.getBean(BaseUtils.class)
        );
    }

    @Override
    @Transactional
    public Response<DataVO<Long>> create(@NonNull AuthUserCreateVO vo) {

        validator.validOnCreate(vo);

        AuthUser authUser = AuthUser
                .childBuilder()
                .username(vo.getUsername())
                .password(utils.encode(vo.getPassword()))
                .email(vo.getEmail())
                .build();
        System.out.println(authUser.getRole().name());
        dao.save(authUser);
        return new Response<>(new DataVO<>(authUser.getId()), 200);

    }

    @Override
    public Response<DataVO<Void>> update(@NonNull AuthUserUpdateVO vo) {
        return null;
    }

    @Override
    public Response<DataVO<Void>> delete(@NonNull Long id) {
        return null;
    }

    @Override
    public Response<DataVO<AuthUserVO>> get(@NonNull Long id) {
        return null;
    }

    @Override
    public Response<DataVO<List<AuthUserVO>>> getAll() {
        return null;
    }

    public Response<DataVO<List<AuthUserVO>>> getAll(AuthRole role) {
        List<AuthUserVO> response = new ArrayList<>();
        List<AuthUser> resultList = dao.findAll(role);
        if (resultList.isEmpty()) {
            return new Response<>(new DataVO<>(AppErrorVO.builder()
                    .friendlyMessage("No user find with role '%s'".formatted(role.name()))
                    .build()));
        }

        for (AuthUser authUser : resultList) {
            AuthUserVO authUserVO = AuthUserVO.childBuilder()
                    .id(authUser.getId())
                    .email(authUser.getEmail())
                    .username(authUser.getUsername())
                    .role(authUser.getRole())
                    .email(authUser.getEmail())
                    .createdAt(authUser.getCreatedAt().toLocalDateTime())
                    .build();

            response.add(authUserVO);
        }

        return new Response<>(new DataVO<>(response), 200);
    }

    public static AuthUserService getInstance() {
        if (instance == null) {
            instance = new AuthUserService();
        }
        return instance;
    }

    public Response<DataVO<AuthUserVO>> login(String username, String password) {
        Optional<AuthUser> userByUsername = dao.findByUserName(username);
        if (userByUsername.isEmpty())
            throw new RuntimeException("user not found");

        AuthUser authUser = userByUsername.get();

        boolean hasPasswordMatched = utils.matchPassword(password, authUser.getPassword());
        if (!hasPasswordMatched)
            throw new RuntimeException("Bad credentials");

        AuthUserVO authUserVO = AuthUserVO.childBuilder()
                .username(authUser.getUsername())
                .role(authUser.getRole())
                .email(authUser.getEmail())
                .id(authUser.getId())
                .createdAt(authUser.getCreatedAt().toLocalDateTime())
                .build();

        Session.setSessionUser(authUserVO);
        return new Response<>(new DataVO<>(authUserVO), 200);
    }

    public void setRole(Long user_id, AuthRole option) {

        Optional<AuthUser> findById = Optional.ofNullable(dao.findById(user_id));
        if (findById.isEmpty())
            throw new RuntimeException("user not found");

        AuthUser authUser = findById.get();

        authUser.setRole(option);

        dao.update(authUser);

    }
}
