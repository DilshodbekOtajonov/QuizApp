package uz.jl.service;

import lombok.NonNull;
import uz.jl.configs.ApplicationContextHolder;
import uz.jl.dao.AbstractDAO;
import uz.jl.dao.GenericDAO;
import uz.jl.dao.subject.SubjectDAO;
import uz.jl.utils.BaseUtils;
import uz.jl.vo.http.DataVO;
import uz.jl.vo.http.Response;
import uz.jl.vo.subject.SubjectCreateVO;
import uz.jl.vo.subject.SubjectUpdateVO;
import uz.jl.vo.subject.SubjectVO;

import java.util.List;

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

    @Override
    public Response<DataVO<List<SubjectVO>>> getAll() {
        return null;
    }
}
