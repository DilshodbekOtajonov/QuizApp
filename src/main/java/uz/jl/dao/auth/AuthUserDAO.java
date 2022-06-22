package uz.jl.dao.auth;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.hibernate.Session;
import org.hibernate.query.NativeQuery;
import org.hibernate.query.Query;
import uz.jl.dao.GenericDAO;
import uz.jl.domains.auth.AuthUser;
import uz.jl.enums.AuthRole;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class AuthUserDAO extends GenericDAO<AuthUser, Long> {
    private static AuthUserDAO instance;

    public static AuthUserDAO getInstance() {
        if (Objects.isNull(instance)) {
            instance = new AuthUserDAO();
        }
        return instance;
    }

    public Optional<AuthUser> findByUserName(String username) {
        Session session = getSession();
        session.beginTransaction();
        Query<AuthUser> query = session
                .createQuery("select t from AuthUser t where lower(t.username) = lower(:username) ",
                        AuthUser.class);
        query.setParameter("username", username);
        Optional<AuthUser> result = Optional.ofNullable(query.getSingleResultOrNull());
        session.close();
        return result;

    } public Optional<AuthUser> findByEmail(String email) {
        Session session = getSession();
        session.beginTransaction();
        Query<AuthUser> query = session
                .createQuery("select t from AuthUser t where lower(t.email) = lower(:email) ",
                        AuthUser.class);
        query.setParameter("email", email);
        Optional<AuthUser> result = Optional.ofNullable(query.getSingleResultOrNull());
        session.close();
        return result;
    }
    public List<AuthUser> findAll(AuthRole role) {
        Session session = getSession();
        session.beginTransaction();
        List<AuthUser> resultList = session.createQuery("select t from AuthUser t where t.role=:role", AuthUser.class).setParameter("role",role).getResultList();

        session.close();
        return resultList;
    }


    public void update(AuthUser entity) {
        Session session = getSession();
        session.beginTransaction();

        Query nativeQuery = session.createQuery("update AuthUser  set role=:role where id=:id");
        nativeQuery.setParameter("role",entity.getRole().toString());
        nativeQuery.setParameter("id",entity.getId());
        session.getTransaction().commit();
        session.close();
    }
}
