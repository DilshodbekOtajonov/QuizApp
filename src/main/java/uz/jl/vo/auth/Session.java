package uz.jl.vo.auth;

import lombok.Getter;
import lombok.Setter;
import uz.jl.enums.AuthRole;

import java.util.Objects;

/**
 * @author "Otajonov Dilshodbek
 * @since 6/21/22 5:09 PM (Tuesday)
 * jira/IntelliJ IDEA
 */
public class Session {
    public static SessionUser sessionUser;


    public static void setSessionUser(AuthUserVO authUserVO) {
        if (Objects.isNull(authUserVO))
            sessionUser = null;
        else sessionUser = new SessionUser(authUserVO);
    }

    @Getter
    @Setter
    public static class SessionUser {

        private Long id;
        private String username;

        public AuthRole role;

        public SessionUser(AuthUserVO session) {

            this.id = session.getId();
            this.username = session.getUsername();
            this.role = session.getRole();
        }

    }
}
