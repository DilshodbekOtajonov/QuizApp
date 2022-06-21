package uz.jl;

import uz.jl.configs.ApplicationContextHolder;
import uz.jl.configs.PasswordConfigurer;
import uz.jl.dao.auth.AuthUserDAO;
import uz.jl.domains.auth.AuthUser;
import uz.jl.service.auth.AuthUserService;
import uz.jl.vo.auth.AuthUserCreateVO;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        AuthUserService service = ApplicationContextHolder.getBean(AuthUserService.class);

        AuthUserCreateVO vo = AuthUserCreateVO.builder()
                .username(new Scanner(System.in).nextLine())
                .email(new Scanner(System.in).nextLine())
                .password(new Scanner(System.in).nextLine())
                .build();
        service.create(vo);

    }
}