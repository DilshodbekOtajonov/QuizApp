package uz.jl.service;

import lombok.NonNull;
import uz.jl.configs.ApplicationContextHolder;
import uz.jl.dao.AbstractDAO;
import uz.jl.dao.GenericDAO;
import uz.jl.dao.subject.SubjectDAO;
import uz.jl.domains.subject.SubjectEntity;
import uz.jl.utils.BaseUtils;
import uz.jl.vo.http.AppErrorVO;
import uz.jl.vo.http.DataVO;
import uz.jl.vo.http.Response;
import uz.jl.vo.subject.SubjectCreateVO;
import uz.jl.vo.subject.SubjectUpdateVO;
import uz.jl.vo.subject.SubjectVO;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static org.reflections.util.ConfigurationBuilder.build;

/**
 * @author "Otajonov Dilshodbek
 * @since 6/23/22 7:49 AM (Thursday)
 * QuizApp/IntelliJ IDEA
 */
public class SubjectService extends AbstractDAO<SubjectDAO> implements GenericCRUDService<
        SubjectVO,
        SubjectCreateVO,
        SubjectUpdateVO,
        Long> {

    private static SubjectService instance;

    private SubjectService() {
        super(
                ApplicationContextHolder.getBean(SubjectDAO.class),
                ApplicationContextHolder.getBean(BaseUtils.class));
    }

    public static SubjectService getInstance() {
        if (instance == null) {
            instance = new SubjectService();
        }
        return instance;
    }


    @Override
    public Response<DataVO<Long>> create(@NonNull SubjectCreateVO vo) {
        return null;
    }

    @Override
    public Response<DataVO<Void>> update(@NonNull SubjectUpdateVO vo) {
        return null;
    }

    @Override
    public Response<DataVO<Void>> delete(@NonNull Long aLong) {
        return null;
    }

    @Override
    public Response<DataVO<SubjectVO>> get(@NonNull Long aLong) {
        return null;
    }

    public Response<DataVO<SubjectVO>> get(@NonNull String subjectName) {
        SubjectEntity subjectEntity = dao.findByName(subjectName);
        if (Objects.isNull(subjectEntity)) {
            return new Response<>(new DataVO<>(AppErrorVO.builder()
                    .friendlyMessage("No subject found").build()), 500);
        }
        SubjectVO subjectVO = SubjectVO.childBuilder()
                .id(subjectEntity.getId())
                .title(subjectEntity.getTitle())
                .build();


        return new Response<>(new DataVO<>(subjectVO), 200);
    }

    @Override
    public Response<DataVO<List<SubjectVO>>> getAll() {
        List<SubjectEntity> all = dao.findAll();
        if (Objects.isNull(all))
            return new Response<>(new DataVO<>(AppErrorVO.builder()
                    .friendlyMessage("No subjects found")
                    .build()));

        List<SubjectVO> result = new ArrayList<>();
        for (SubjectEntity subjectEntity : all) {
            SubjectVO subjectVO = SubjectVO.childBuilder()
                    .title(subjectEntity.getTitle())
                    .id(subjectEntity.getId())
                    .build();
            result.add(subjectVO);
        }

        return new Response<>(new DataVO<>(result));
    }
}
