package uz.jl;

import uz.jl.configs.PasswordConfigurer;
import uz.jl.dao.auth.AuthUserDAO;
import uz.jl.domains.auth.AuthUser;

public class Main {
    public static void main(String[] args) {
        AuthUserDAO authUserDAO = new AuthUserDAO();
        AuthUser authUser = new AuthUser();
        authUser.setCreatedBy(-1L);
        authUser.setUsername("Mr.JL");
        authUser.setPassword(PasswordConfigurer.encode("123"));
        authUserDAO.save(authUser);
    }
}