package uz.jl.vo.auth;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Objects;

/**
 * @author "Otajonov Dilshodbek
 * @since 6/21/22 5:09 PM (Tuesday)
 * jira/IntelliJ IDEA
 */
public class Session {
    private static SessionUser sessionUser;


    public static void setSessionUser(AuthUserVO authUserVO) {
        sessionUser = new SessionUser(authUserVO);
    }

    private static class SessionUser {

        private Long id;
        private String username;

        public SessionUser(AuthUserVO session) {
            if (Objects.nonNull(session)) {
                this.id = session.getId();
                this.username = session.getUsername();
            }else {
                id=null;
                username=null;
            }
        }

    }
}
