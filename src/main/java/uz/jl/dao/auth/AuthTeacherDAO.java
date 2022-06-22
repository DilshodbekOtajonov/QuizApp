package uz.jl.dao.auth;

import org.hibernate.Session;
import org.hibernate.query.Query;
import uz.jl.dao.BaseDAO;
import uz.jl.domains.auth.AuthUser;

import java.util.Optional;

public class AuthTeacherDAO implements BaseDAO{


    private Session session;

    public Optional<AuthUser> getAllTeachers() {
        Session session = getSession();
        session.beginTransaction();
        Query<AuthUser> query = session
                .createQuery("select t from AuthUser t where lower(t.role) = lower(:TEACHER) ",
                        AuthUser.class);
        Optional<AuthUser> result = Optional.ofNullable(query.getSingleResultOrNull());
        session.close();
        return result;

    }


    public Session getSession() {

        return session;
    }

    public void setSession(Session session) {
        this.session = session;
    }
}
