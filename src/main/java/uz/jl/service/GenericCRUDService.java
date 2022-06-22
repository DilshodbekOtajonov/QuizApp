package uz.jl.service;

import lombok.NonNull;
import uz.jl.vo.BaseVO;
import uz.jl.vo.GenericVO;
import uz.jl.vo.http.DataVO;
import uz.jl.vo.http.Response;

import java.io.Serializable;
import java.util.List;

/**
 * @param <VO>  value object for reading
 * @param <CVO> create value object for creating entity
 * @param <UVO> update value object for updating existing entity
 * @param <ID>  field type that annotated with @Id
 */
public interface GenericCRUDService<
        VO extends GenericVO,
        CVO extends BaseVO,
        UVO extends GenericVO,
        ID extends Serializable> {
    Response<DataVO<ID>> create(@NonNull CVO vo);

    Response<DataVO<Void>> update(@NonNull UVO vo);

    Response<DataVO<Void>> delete(@NonNull ID id);

    Response<DataVO<VO>> get(@NonNull ID id);

    Response<DataVO<List<VO>>> getAll();
}
