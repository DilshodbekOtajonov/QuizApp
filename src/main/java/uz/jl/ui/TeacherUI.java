package uz.jl.ui;

import uz.jl.BaseUtils;
import uz.jl.vo.auth.Session;

import java.util.Objects;

/**
 * @author "Otajonov Dilshodbek
 * @since 6/23/22 8:08 AM (Thursday)
 * QuizApp/IntelliJ IDEA
 */
public class TeacherUI {
    public static void main(String[] args) {

        if (Objects.nonNull(Session.sessionUser)) {
            BaseUtils.println("CRUD questions -> 1 ");
            BaseUtils.println("Settings  -> 2");
            BaseUtils.println("Log Out  -> l");
            BaseUtils.println("Quit  -> q");

            String option = BaseUtils.readText("Insert option: ");

            switch (option) {
                case "1" -> crud();
                case "2" -> settings();
                case "l" -> Session.setSessionUser(null);
                case "q" -> System.exit(0);
                default -> BaseUtils.println("Wrong option");
            }
            main(args);
        }
    }

    private static void settings() {
        BaseUtils.println("Change username -> 1 ");
        BaseUtils.println("Change password -> 2 ");

        String option = BaseUtils.readText("insert option");
        switch (option) {
            case "1" -> StudentUI.changeUserName();
            case "2" -> StudentUI.changePassword();
            default -> BaseUtils.println("Wrong option");
        }

    }

    private static void crud() {
        BaseUtils.println("create questions-> 1 ");
        BaseUtils.println("Settings  -> 2");
        BaseUtils.println("Quit  -> q");
    }
}
