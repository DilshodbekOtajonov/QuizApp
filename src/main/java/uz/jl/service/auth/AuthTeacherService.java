package uz.jl.service.auth;

import uz.jl.configs.ApplicationContextHolder;
import uz.jl.dao.AbstractDAO;
import uz.jl.dao.auth.AuthTeacherDAO;
import uz.jl.dao.auth.AuthUserDAO;
import uz.jl.domains.auth.AuthUser;
import uz.jl.service.GenericCRUDService;
import uz.jl.utils.BaseUtils;
import uz.jl.vo.auth.AuthUserCreateVO;
import uz.jl.vo.auth.AuthUserUpdateVO;
import uz.jl.vo.auth.AuthUserVO;
import uz.jl.vo.auth.Session;
import uz.jl.vo.http.DataVO;
import uz.jl.vo.http.Response;

import java.util.List;
import java.util.Optional;

public class AuthTeacherService extends AbstractDAO<AuthTeacherDAO> {


    private static AuthTeacherService instance;

    public AuthTeacherService() {
        super(
                ApplicationContextHolder.getBean(AuthTeacherDAO.class),
                ApplicationContextHolder.getBean(BaseUtils.class)
        );
    }


    public Response<DataVO<List<AuthUserVO>>> getAllTeachers() {
        return null;

    }


    public Response<DataVO<AuthUserVO>> login(String username, String password) {
        Optional<AuthUser> allTeachers = dao.getAllTeachers();


        AuthUser authUser = allTeachers.get();


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

    public AuthTeacherService(AuthTeacherDAO dao, BaseUtils utils) {
        super(dao, utils);
    }
}
