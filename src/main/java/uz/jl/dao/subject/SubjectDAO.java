package uz.jl.dao.subject;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.hibernate.Session;
import uz.jl.dao.GenericDAO;
import uz.jl.domains.subject.SubjectEntity;

/**
 * @author "Otajonov Dilshodbek
 * @since 6/23/22 7:50 AM (Thursday)
 * QuizApp/IntelliJ IDEA
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class SubjectDAO extends GenericDAO<SubjectEntity, Long> {
    private static SubjectDAO instance;

    public static SubjectDAO getInstance() {
        if (instance == null) {
            instance = new SubjectDAO();
        }
        return instance;
    }

    public SubjectEntity findByName(String name) {
        Session session = getSession();
        session.beginTransaction();

        SubjectEntity result = session.createQuery("select t from SubjectEntity t where  lower(t.title) = lower(:name) ", SubjectEntity.class)
                .setParameter("name", name)
                .getSingleResultOrNull();

        session.close();
        return result;
    }

}
