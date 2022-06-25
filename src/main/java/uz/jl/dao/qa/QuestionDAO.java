package uz.jl.dao.qa;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.hibernate.Session;
import uz.jl.dao.GenericDAO;
import uz.jl.domains.QA.QuestionEntity;
import uz.jl.enums.QuestionStatus;

import java.util.List;


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


    public List<QuestionEntity> findAllBySubjectId(Long subjectId) {
        Session session = getSession();
        session.beginTransaction();
        List<QuestionEntity> entityList = session.createQuery("select t from QuestionEntity t where t.subject.id=:id", QuestionEntity.class).setParameter("id", subjectId).getResultList();
        session.close();
        return entityList;
    }

    public List<QuestionEntity> findAllBySubjectIdAndLevel(Long subjectId, QuestionStatus status) {
        Session session = getSession();
        session.beginTransaction();
        List<QuestionEntity> entityList = session.createQuery("select t from QuestionEntity t where t.status=:status  and t.subject.id=:id", QuestionEntity.class)
                .setParameter("id", subjectId)
                .setParameter("status", status).getResultList();
        session.close();
        return entityList;
    }

    public List<QuestionEntity> findAllBySubjectIdAndLevel(Long subjectId, QuestionStatus status, Integer numberOfQuestion) {
        Session session = getSession();
        session.beginTransaction();
        List<QuestionEntity> entityList = session.createQuery("select t from QuestionEntity t where t.status=:status  and t.subject.id=:id", QuestionEntity.class)
                .setParameter("id", subjectId)
                .setParameter("status", status)
                .setMaxResults(numberOfQuestion)
                .getResultList();
        session.close();
        return entityList;
    }

}
