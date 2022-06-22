package uz.jl.vo.auth;

import lombok.*;
import uz.jl.enums.AuthRole;
import uz.jl.vo.GenericVO;

import java.time.LocalDateTime;


@Getter
@Setter
@ToString
public class AuthUserVO extends GenericVO {
    private String username;
    private String email;
    private AuthRole role;
    private LocalDateTime createdAt;
    @Builder(builderMethodName = "childBuilder")
    public AuthUserVO(Long id, String username, String email, AuthRole role, LocalDateTime createdAt) {
        super(id);
        this.username = username;
        this.email = email;
        this.role = role;
        this.createdAt = createdAt;
    }
}
