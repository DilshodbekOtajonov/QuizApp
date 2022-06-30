package uz.jl.dao.qa;

import jakarta.persistence.criteria.CriteriaBuilder;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.hibernate.Session;
import org.hibernate.query.criteria.JpaCriteriaInsertSelect;
import uz.jl.dao.GenericDAO;
import uz.jl.domains.QA.QuestionEntity;
import uz.jl.enums.QuestionStatus;

import java.sql.SQLException;
import java.util.*;


/**
 * @author "Otajonov Dilshodbek
 * @since 6/22/22 2:19 PM (Wednesday)
 * QuizApp/IntelliJ IDEA
 */

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class QuestionDAO extends GenericDAO<QuestionEntity, Long> {
    private static QuestionDAO instance;
    private static Random random = new Random();

    public static QuestionDAO getInstance() {
        if (instance == null) {
            instance = new QuestionDAO();
        }
        return instance;
    }


    public List<QuestionEntity> findAllBySubjectId(Long subjectId) {
        Session session = getSession();
        session.beginTransaction();
        List<QuestionEntity> entityList = session.createQuery("select t from QuestionEntity t where t.subject.id=:id and t.deleted=false ", QuestionEntity.class).setParameter("id", subjectId).getResultList();
        session.close();
        return entityList;
    }

    public List<QuestionEntity> findAllBySubjectIdAndLevel(Long subjectId, QuestionStatus status) {
        Session session = getSession();
        session.beginTransaction();
        List<QuestionEntity> entityList = session.createQuery("select t from QuestionEntity t where t.status=:status  and t.subject.id=:id and t.deleted=false ", QuestionEntity.class)
                .setParameter("id", subjectId)
                .setParameter("status", status).getResultList();
        session.close();
        return entityList;
    }

    public List<QuestionEntity> findAllBySubjectIdAndLevel(Long subjectId, QuestionStatus status, Integer numberOfQuestion) {
        Session session = getSession();
        session.beginTransaction();

//        List<QuestionEntity> resultList = session.createNativeQuery("select distinct t.* from question.questions t inner join question.answers a on t.id = a.question_id where t.subject_id=:subjectId and t.status=:status order by Random()", QuestionEntity.class)
//                .setParameter("subjectId", subjectId)
//                .setParameter("status", status)
//                .setMaxResults(numberOfQuestion)
//                .getResultList();
//        return resultList;

        List<QuestionEntity> entityList = session.createQuery("select t from QuestionEntity t where t.status=:status and t.subject.id=:id and t.deleted=false ", QuestionEntity.class)
                .setParameter("id", subjectId)
                .setParameter("status", status)
//                .setMaxResults(numberOfQuestion)
                .getResultList();
        session.close();

        Set<QuestionEntity> resultList  = new HashSet<>();
        for (int i = 0; i < numberOfQuestion; i++) {
            QuestionEntity questionEntity = entityList.get(random.nextInt(entityList.size()));
            resultList.add(questionEntity);
        }

        return resultList.stream().toList();
    }

    public static void main(String[] args) {
        instance = getInstance();
        List<QuestionEntity> allBySubjectIdAndLevel = instance.findAllBySubjectIdAndLevel(1L, QuestionStatus.EASY, 1);
        allBySubjectIdAndLevel.forEach(System.out::println);
    }
}
