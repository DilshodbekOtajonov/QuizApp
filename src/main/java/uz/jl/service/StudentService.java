package uz.jl.service;

import lombok.NonNull;
import uz.jl.configs.ApplicationContextHolder;
import uz.jl.dao.AbstractDAO;
import uz.jl.dao.StudentDAO;
import uz.jl.dao.auth.AuthUserDAO;
import uz.jl.domains.auth.AuthUser;
import uz.jl.domains.auth.StudentEntity;
import uz.jl.exceptions.ValidationException;

import uz.jl.utils.BaseUtils;
import uz.jl.utils.validators.student.StudentValidator;

import uz.jl.vo.auth.Session;
import uz.jl.vo.http.AppErrorVO;
import uz.jl.vo.http.DataVO;
import uz.jl.vo.http.Response;
import uz.jl.vo.student.StudentCreateVO;
import uz.jl.vo.student.StudentUpdateVO;
import uz.jl.vo.student.StudentVO;

import java.util.List;

public class StudentService extends AbstractDAO<StudentDAO> implements GenericCRUDService<
        StudentVO,
        StudentCreateVO,
        StudentUpdateVO,
        Long> {{
}

    private final StudentValidator studentValidator = ApplicationContextHolder.getBean(StudentValidator.class);


    private static AuthUserDAO authUserDAO = ApplicationContextHolder.getBean(AuthUserDAO.class);

    private static StudentService instance;


    public StudentService() {

            super(
                    ApplicationContextHolder.getBean(StudentDAO.class),
                    ApplicationContextHolder.getBean(BaseUtils.class));
    }

    public static StudentService getInstance() {
        if (instance == null) {
            instance = new StudentService();
        }
        return instance;
    }

    @Override
    public Response<DataVO<Long>> create(@NonNull StudentCreateVO vo) {
        try {
            studentValidator.validOnCreate(vo);

            AuthUser authUser = authUserDAO.findById(Session.sessionUser.getId());
            StudentEntity student = StudentEntity
                    .builder()
                    .name(vo.getName())
                    .surname(vo.getSurname())
                    .user(authUser)
                    .build();

            StudentEntity save = dao.save(student);


            return new Response<>(new DataVO<>(save.getId()), 200);
        } catch (ValidationException e) {
            return new Response<>(new DataVO<>(AppErrorVO.builder()
                    .friendlyMessage(e.getMessage())
                    .build()), 400);
        }
    }

    @Override
    public Response<DataVO<Void>> update(@NonNull StudentUpdateVO vo) {
        return null;
    }

    @Override
    public Response<DataVO<Void>> delete(@NonNull Long aLong) {
        return null;
    }

    @Override
    public Response<DataVO<StudentVO>> get(@NonNull Long aLong) {
        return null;
    }

    @Override
    public Response<DataVO<List<StudentVO>>> getAll() {
        return null;
    }
}
