package uz.jl.ui;

import uz.jl.BaseUtils;
import uz.jl.vo.auth.Session;

import java.util.Objects;

/**
 * @author "Otajonov Dilshodbek
 * @since 6/22/22 2:30 PM (Wednesday)
 * QuizApp/IntelliJ IDEA
 */
public class StudentUI {
    public static void main(String[] args) {
        if (Objects.isNull(Session.sessionUser)) {
            System.out.println("=================User Page=================");
            BaseUtils.println("Do test -> 1");
            BaseUtils.println("Show history ->2");
            BaseUtils.println("Change auth info -> 3");
            BaseUtils.println("logout -> 4");
            BaseUtils.println("Quit -> q");

            String choice = BaseUtils.readText("choice ? ");
            switch (choice) {
                case "1" -> doTest();
                case "2" -> showHistory();
                case "3" -> updateAuthInfo();
                case "4" -> Session.setSessionUser(null);
                case "q" -> {
                    BaseUtils.println("Bye");
                    System.exit(0);
                }
                default -> BaseUtils.println("Invalid choice");
            }
            main(args);
        }
    }

    private static void updateAuthInfo() {

    }

    private static void showHistory() {

    }

    private static void doTest() {


    }
}
