package uz.jl.dao.variant;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import uz.jl.dao.GenericDAO;
import uz.jl.domains.VariantEntity;

/**
 * @author "Otajonov Dilshodbek
 * @since 6/22/22 3:33 PM (Wednesday)
 * QuizApp/IntelliJ IDEA
 */

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class VariantDAO extends GenericDAO<VariantEntity, Long> {
    private static VariantDAO instance;

    public static VariantDAO getInstance() {
        if (instance == null) {
            instance = new VariantDAO();
        }
        return instance;
    }
}
