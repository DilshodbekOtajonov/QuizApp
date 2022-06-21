package uz.jl.vo.auth;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import uz.jl.enums.AuthRole;
import uz.jl.enums.Status;
import uz.jl.vo.GenericVO;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuthUserUpdateVO extends GenericVO {
    private String username;
    private String email;
    private AuthRole role;
    private Status status;
}
