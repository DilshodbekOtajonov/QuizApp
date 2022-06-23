package uz.jl.service;

import lombok.NonNull;
import uz.jl.configs.ApplicationContextHolder;
import uz.jl.dao.AbstractDAO;
import uz.jl.dao.variant.VariantDAO;
import uz.jl.utils.BaseUtils;
import uz.jl.vo.http.DataVO;
import uz.jl.vo.http.Response;
import uz.jl.vo.variant.VariantCreateVO;
import uz.jl.vo.variant.VariantUpdateVO;
import uz.jl.vo.variant.VariantVO;

import java.util.List;

/**
 * @author "Otajonov Dilshodbek
 * @since 6/23/22 8:50 AM (Thursday)
 * QuizApp/IntelliJ IDEA
 */
public class VariantService extends AbstractDAO<VariantDAO> implements GenericCRUDService<
        VariantVO,
        VariantCreateVO,
        VariantUpdateVO,
        Long> {

    private VariantService() {
        super(
                ApplicationContextHolder.getBean(VariantDAO.class),
                ApplicationContextHolder.getBean(BaseUtils.class));
    }

    @Override
    public Response<DataVO<Long>> create(@NonNull VariantCreateVO vo) {
        return null;
    }

    @Override
    public Response<DataVO<Void>> update(@NonNull VariantUpdateVO vo) {
        return null;
    }

    @Override
    public Response<DataVO<Void>> delete(@NonNull Long aLong) {
        return null;
    }

    @Override
    public Response<DataVO<VariantVO>> get(@NonNull Long aLong) {
        return null;
    }

    @Override
    public Response<DataVO<List<VariantVO>>> getAll() {
        return null;
    }
}
