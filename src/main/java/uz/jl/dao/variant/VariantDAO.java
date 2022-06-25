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


    public List<VariantEntity> findByStudentId(Long studentId) {
        Session session = getSession();
        session.beginTransaction();

        List<VariantEntity> result = session.createQuery("select t from VariantEntity t where t.user.id=:studentId", VariantEntity.class)
                .setParameter("studentId", studentId).getResultList();

        session.getTransaction().commit();
        session.close();
        return result;
    }
}
