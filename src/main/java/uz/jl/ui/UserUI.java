package uz.jl.ui;

import uz.jl.BaseUtils;
import uz.jl.configs.ApplicationContextHolder;
import uz.jl.enums.AuthRole;
import uz.jl.service.auth.AuthUserService;
import uz.jl.vo.auth.Session;

/**
 * @author "Otajonov Dilshodbek
 * @since 6/24/22 3:36 PM (Friday)
 * QuizApp/IntelliJ IDEA
 */
public class UserUI {

    private static final AuthUserService authUserService = ApplicationContextHolder.getBean(AuthUserService.class);

    public static void main(String[] args) {
        BaseUtils.println("1 -> Login as Student");
        BaseUtils.println("2 -> Login as Teacher");
        BaseUtils.println("l -> LogOut");
        BaseUtils.println("q -> Quit");
        String choice = BaseUtils.readText("choice ? ");

        switch (choice) {
            case "1" -> setUserAsStudent();
            case "2" -> setUserAsTeacher();
            case "l" -> Session.setSessionUser(null);
            case "q" -> {
                BaseUtils.println("Bye");
                System.exit(0);
            }
            default -> BaseUtils.println("Invalid choice");
        }
    }

    private static void setUserAsTeacher() {
        authUserService.setRole(Session.sessionUser.getId(), AuthRole.TEACHER);

    }

    private static void setUserAsStudent() {
        authUserService.setRole(Session.sessionUser.getId(), AuthRole.STUDENT);
    }
}

