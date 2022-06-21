package uz.jl.vo.auth;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import uz.jl.enums.AuthRole;
import uz.jl.vo.GenericVO;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuthUserVO extends GenericVO {
    private String username;
    private String email;
    private AuthRole role;
    private LocalDateTime createdAt;
}
