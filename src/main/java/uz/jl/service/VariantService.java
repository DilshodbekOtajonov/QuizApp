package uz.jl.service;

import uz.jl.configs.ApplicationContextHolder;
import uz.jl.dao.AbstractDAO;
import uz.jl.dao.variant.VariantDAO;
import uz.jl.utils.BaseUtils;

/**
 * @author "Otajonov Dilshodbek
 * @since 6/23/22 8:50 AM (Thursday)
 * QuizApp/IntelliJ IDEA
 */
public class VariantService extends AbstractDAO<VariantDAO> {

    private VariantService() {
        super(
                ApplicationContextHolder.getBean(VariantDAO.class),
                ApplicationContextHolder.getBean(BaseUtils.class));
    }
}
