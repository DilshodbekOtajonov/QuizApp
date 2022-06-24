package uz.jl.service.auth;

import lombok.NonNull;
import uz.jl.configs.ApplicationContextHolder;
import uz.jl.dao.AbstractDAO;
import uz.jl.dao.auth.AuthUserDAO;
import uz.jl.domains.auth.AuthUser;
import uz.jl.enums.AuthRole;
import uz.jl.exceptions.ValidationException;
import uz.jl.service.GenericCRUDService;
import uz.jl.utils.BaseUtils;
import uz.jl.utils.validators.authUser.AuthUserValidator;
import uz.jl.vo.auth.*;
import uz.jl.vo.http.AppErrorVO;
import uz.jl.vo.http.DataVO;
import uz.jl.vo.http.Response;

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
    public Response<DataVO<Long>> create(@NonNull AuthUserCreateVO vo) {
        try {
            validator.validOnCreate(vo);

            AuthUser authUser = AuthUser
                    .childBuilder()
                    .username(vo.getUsername())
                    .password(utils.encode(vo.getPassword()))
                    .email(vo.getEmail())
                    .build();
            System.out.println(authUser.getRole().name());
            AuthUser save = dao.save(authUser);
            Session.setSessionUser(AuthUserVO.childBuilder()
                    .id(save.getId())
                    .role(save.getRole())
                    .username(save.getUsername())
                    .createdAt(save.getCreatedAt().toLocalDateTime())
                    .build());
            return new Response<>(new DataVO<>(authUser.getId()), 200);
        } catch (ValidationException e) {
            return new Response<>(new DataVO<>(AppErrorVO.builder()
                    .friendlyMessage(e.getMessage())
                    .build()), 400);
        }

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
        try {
            validator.validateKey(id);
            AuthUser authUser = dao.findById(id);
            AuthUserVO authUserVO = AuthUserVO.childBuilder()
                    .id(id)
                    .role(authUser.getRole())
                    .username(authUser.getUsername())
                    .email(authUser.getEmail())
                    .createdAt(authUser.getCreatedAt().toLocalDateTime())
                    .build();

            return new Response<>(new DataVO<>(authUserVO));
        } catch (ValidationException e) {
            return new Response<>(new DataVO<>(AppErrorVO.builder()
                    .friendlyMessage(e.getMessage())
                    .build()));
        }
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

    public Response<DataVO<AuthUserVO>> login(String username, String password) {
        Optional<AuthUser> userByUsername = dao.findByUserName(username);
        if (userByUsername.isEmpty())
            return new Response<>(new DataVO<>(AppErrorVO.builder()
                    .friendlyMessage("user not found")
                    .build()));

        AuthUser authUser = userByUsername.get();

        boolean hasPasswordMatched = utils.matchPassword(password, authUser.getPassword());
        if (!hasPasswordMatched)
            return new Response<>(new DataVO<>(AppErrorVO.builder()
                    .friendlyMessage("Bad credentials")
                    .build()));

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

    public Response<DataVO<Void>> changeUsername(String newUsername) {
        Optional<AuthUser> usernameCheck = dao.findByUserName(newUsername);
        if (usernameCheck.isPresent())
            return new Response<>(new DataVO<>(AppErrorVO.builder()
                    .friendlyMessage("username '%s' already taken".formatted(newUsername))
                    .build()), 400);
        AuthUser authUser = dao.findById(Session.sessionUser.getId());
        authUser.setUsername(newUsername);
        dao.update(authUser);
        return new Response<>(new DataVO<>(null), 200);
    }

    public Response<DataVO<Void>> changePassword(AuthUserPasswordResetVO resetVO) {
        AuthUser authUser = dao.findById(Session.sessionUser.getId());

        boolean matchPassword = utils.matchPassword(resetVO.getOldPassword(), authUser.getPassword());
        if (!matchPassword)
            return new Response<>(new DataVO<>(AppErrorVO.builder()
                    .friendlyMessage("Old password incorrect")
                    .build()), 404);

        if (!resetVO.getNewPassword().equals(resetVO.getConfirmNewPassword()))
            return new Response<>(new DataVO<>(AppErrorVO.builder()
                    .friendlyMessage("new password not confirmed")
                    .build()), 400);

        String encode = utils.encode(resetVO.getNewPassword());
        authUser.setPassword(encode);
        dao.update(authUser);
        return new Response<>(new DataVO<>(null), 200);
    }


    public static AuthUserService getInstance() {
        if (instance == null) {
            instance = new AuthUserService();
        }
        return instance;
    }
}
