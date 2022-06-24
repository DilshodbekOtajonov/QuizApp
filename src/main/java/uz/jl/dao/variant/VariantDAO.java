package uz.jl.dao.variant;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.hibernate.Session;
import uz.jl.dao.GenericDAO;
import uz.jl.domains.QA.VariantEntity;

import java.util.List;

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

    public List<VariantEntity> findAll(Long user_id,Long variant_id) {
        Session session = getSession();
        session.beginTransaction();
        List<VariantEntity> resultList = session.createQuery("select t from VariantEntity t where t.user.id=:user_id", VariantEntity.class).setParameter("user_id", user_id).getResultList();
        session.close();
        return resultList;
    }

    public List<VariantEntity> findAllVariantId(Long user_id, Long variant_id) {
        Session session = getSession();
        session.beginTransaction();
        List<VariantEntity> resultList = session.createQuery("select t from VariantEntity t where t.id=:variant_id and t.user.id=:user_id", VariantEntity.class).setParameter("user_id", user_id).setParameter("variant_id",variant_id).getResultList();
        session.close();
        return resultList;
    }
}
