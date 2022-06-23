package uz.jl.dao.qa;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.hibernate.Session;
import org.hibernate.query.Query;
import uz.jl.dao.GenericDAO;
import uz.jl.domains.QA.QuestionEntity;
import uz.jl.domains.SubjectEntity;
import uz.jl.enums.QuestionStatus;

import java.util.List;
import java.util.Optional;

/**
 * @author "Otajonov Dilshodbek
 * @since 6/22/22 2:19 PM (Wednesday)
 * QuizApp/IntelliJ IDEA
 */

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class QuestionDAO extends GenericDAO<QuestionEntity, Long> {
    private static QuestionDAO instance;


    public static QuestionDAO getInstance() {
        if (instance == null) {
            instance = new QuestionDAO();
        }
        return instance;
    }


    public Optional<SubjectEntity> findByName(String title) {
        Session session = getSession();
        session.beginTransaction();
        Query<SubjectEntity> query = session
                .createQuery("select t from SubjectEntity t where t.title=:title", SubjectEntity.class);
        query.setParameter("title", title);
        Optional<SubjectEntity> result = Optional.ofNullable(query.getSingleResultOrNull());
        session.close();
        return result;
    }

    public Optional<List<QuestionEntity>> question(Long id, String level) {
        Session session = getSession();
        session.beginTransaction();
        Query<QuestionEntity> query = session
                .createQuery("select t from QuestionEntity t where t.subject=:id and t.status=:level", QuestionEntity.class);
        query.setParameter("subject", id);
        query.setParameter("status", level);
        Optional<List<QuestionEntity>> resultList = Optional.ofNullable(query.getResultList());
        session.close();
        return resultList;
    }

    public List<QuestionEntity> findAll(Long id) {
        Session session = getSession();
        session.beginTransaction();
        List<QuestionEntity> entityList = session.createQuery("select t from QuestionEntity t where t.subject.id=:id", QuestionEntity.class).setParameter("id", id).getResultList();
        session.close();
        return entityList;
    }

    public List<QuestionEntity> findAll(Long id, QuestionStatus status) {
        Session session = getSession();
        session.beginTransaction();
        List<QuestionEntity> entityList = session.createQuery("select t from QuestionEntity t where t.status=:status  and t.subject.id=:id", QuestionEntity.class).setParameter("id", id).setParameter("status", status).getResultList();
        session.close();
        return entityList;
    }
}
