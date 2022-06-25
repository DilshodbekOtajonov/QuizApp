package uz.jl.service;

import lombok.NonNull;
import uz.jl.configs.ApplicationContextHolder;
import uz.jl.dao.AbstractDAO;
import uz.jl.dao.auth.AuthUserDAO;
import uz.jl.dao.subject.SubjectDAO;
import uz.jl.dao.teacher.TeacherDAO;
import uz.jl.domains.auth.AuthUser;
import uz.jl.domains.auth.TeacherEntity;
import uz.jl.domains.subject.SubjectEntity;
import uz.jl.exceptions.ValidationException;
import uz.jl.utils.BaseUtils;
import uz.jl.utils.validators.teacher.TeacherValidator;
import uz.jl.vo.auth.Session;
import uz.jl.vo.http.AppErrorVO;
import uz.jl.vo.http.DataVO;
import uz.jl.vo.http.Response;
import uz.jl.vo.subject.SubjectVO;
import uz.jl.vo.teacher.TeacherCreateVO;
import uz.jl.vo.teacher.TeacherUpdateVO;
import uz.jl.vo.teacher.TeacherVO;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class TeacherService extends AbstractDAO<TeacherDAO> implements GenericCRUDService<
        TeacherVO,
        TeacherCreateVO,
        TeacherUpdateVO,
        Long> {

    private final TeacherValidator teacherValidator = ApplicationContextHolder.getBean(TeacherValidator.class);
    private static AuthUserDAO authUserDAO = ApplicationContextHolder.getBean(AuthUserDAO.class);
    private static SubjectDAO subjectDAO = ApplicationContextHolder.getBean(SubjectDAO.class);
    private static TeacherService instance;

    public static TeacherService getInstance() {
        if (instance == null) {
            instance = new TeacherService();
        }
        return instance;
    }

    public TeacherService() {
        super(
                ApplicationContextHolder.getBean(TeacherDAO.class),
                ApplicationContextHolder.getBean(BaseUtils.class)
        );
    }

    @Override
    public Response<DataVO<Long>> create(@NonNull TeacherCreateVO vo) {
        try {

            teacherValidator.validOnCreate(vo);

            AuthUser authUser = authUserDAO.findById(Session.sessionUser.getId());
            TeacherEntity teacher = TeacherEntity
                    .builder()
                    .name(vo.getName())
                    .surname(vo.getSurname())
                    .user(authUser)
                    .build();

            TeacherEntity save = dao.save(teacher);


            return new Response<>(new DataVO<>(save.getId()), 200);
        } catch (ValidationException e) {
            return new Response<>(new DataVO<>(AppErrorVO.builder()
                    .friendlyMessage(e.getMessage())
                    .build()), 400);
        }

    }

    @Override
    public Response<DataVO<Void>> update(@NonNull TeacherUpdateVO vo) {
        return null;
    }

    @Override
    public Response<DataVO<Void>> delete(@NonNull Long aLong) {
        return null;
    }

    @Override
    public Response<DataVO<TeacherVO>> get(@NonNull Long aLong) {
        return null;
    }

    @Override
    public Response<DataVO<List<TeacherVO>>> getAll() {
        return null;
    }

    public Response<DataVO<Void>> addSubjectList(TeacherVO teacherVO) {
        try {

            TeacherEntity teacherEntity = dao.findByUserId(teacherVO.getId());
            System.out.println(teacherEntity);
            List<SubjectEntity> teacherSubjectList = teacherEntity.getSubjectList();
            for (SubjectVO subjectVO : teacherVO.getSubjectList()) {
                SubjectEntity subjectEntity = subjectDAO.findById(subjectVO.getId());

                if (Objects.isNull(teacherSubjectList))
                    teacherEntity.setSubjectList(new ArrayList<>());
                if (!teacherSubjectList.contains(subjectEntity)) {
                    System.out.println("teacherSubjectList.contains(subjectEntity) = " + teacherSubjectList.contains(subjectEntity));
                    teacherEntity.getSubjectList().add(subjectEntity);
                }
            }
            dao.update(teacherEntity);

            return new Response<>(new DataVO<>(null), 200);
        } catch (Exception e) {
            e.printStackTrace();
            return new Response<>(new DataVO<>(AppErrorVO.builder()
                    .friendlyMessage("Oops something went wrong")
                    .build()), 400);

        }
    }

    public Response<DataVO<List<SubjectEntity>>> getSubjectList(Long userId) {
        try {
            TeacherEntity teacherEntity = dao.findByUserId(userId);
            if (Objects.isNull(teacherEntity.getSubjectList()))
                return new Response<>(new DataVO<>(AppErrorVO.builder()
                        .friendlyMessage("You do not have any subjects")
                        .build()), 500);

            return new Response<>(new DataVO<>(teacherEntity.getSubjectList()), 200);
        } catch (Exception e) {
            return new Response<>(new DataVO<>(AppErrorVO.builder()
                    .friendlyMessage("Oops something went wrong")
                    .build()), 400);
        }
    }
}
