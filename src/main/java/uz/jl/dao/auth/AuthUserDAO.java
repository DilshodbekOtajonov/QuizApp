package uz.jl.dao.auth;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import uz.jl.dao.GenericDAO;
import uz.jl.domains.auth.AuthUser;

import java.util.Objects;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class AuthUserDAO extends GenericDAO<AuthUser, Long> {
    private static AuthUserDAO instance;

    public static AuthUserDAO getInstance() {
        if (Objects.isNull(instance)) {
            instance = new AuthUserDAO();
        }
        return instance;
    }
}
