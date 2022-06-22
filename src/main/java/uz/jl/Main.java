package uz.jl;


import uz.jl.configs.ApplicationContextHolder;
import uz.jl.dao.auth.AuthUserDAO;
import uz.jl.domains.auth.AuthUser;

public class Main {
    public static void main(String[] args) {
        AuthUserDAO authUserDAO= ApplicationContextHolder.getBean(AuthUserDAO.class);
        AuthUser user = authUserDAO.findById(4l);
        System.out.println("user = " + user);

    }
}