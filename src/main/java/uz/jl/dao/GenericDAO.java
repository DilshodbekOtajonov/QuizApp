package uz.jl.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import uz.jl.configs.HibernateConfigurer;

import java.lang.reflect.ParameterizedType;
import java.sql.SQLException;
import java.util.List;
import java.util.Objects;

public class GenericDAO<T, ID> implements BaseDAO {

    protected SessionFactory sessionFactory = HibernateConfigurer.getSessionFactory();
    private Session session = getSession();

    private final Class<T> persistentClass;

    @SuppressWarnings("unchecked")
    public GenericDAO() {
        this.persistentClass = (Class<T>) ((ParameterizedType) getClass()
                .getGenericSuperclass())
                .getActualTypeArguments()[0];
    }

    public T save(T entity) {
        Session currentSession = getSession();
        currentSession.persist(entity);
        currentSession.getTransaction().commit();
        return entity;
    }

    public void deleteById(ID id) throws SQLException {
        T entity = findById(id);
        // TODO: 6/20/2022 create your custom exception here
        if (Objects.isNull(entity)) {
            throw new SQLException("Object not found by given id '%s'".formatted(id));
        }
        getSession().remove(entity);
    }

    public void update(T entity) {
        save(entity);
    }

    public T findById(ID id) {
        return getSession().get(persistentClass, id);
    }

    public List<T> findAll() {
        return getSession()
                .createQuery("from " + persistentClass.getSimpleName(), persistentClass)
                .getResultList();
    }

    protected Session getSession() {
        if (Objects.isNull(sessionFactory) || sessionFactory.isClosed()) {
            sessionFactory = HibernateConfigurer.getSessionFactory();
        }

        if (Objects.isNull(session) || !session.isOpen()) {
            session = sessionFactory.getCurrentSession();
        }
        return session;
    }

}
