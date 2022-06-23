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

    static StudentUI studentUI = new StudentUI();
    public static void main(String[] args) {

        if(Objects.nonNull(Session.sessionUser)){
            BaseUtils.println("CRUD questions -> 1 ");
            BaseUtils.println("Settings  -> 2");
            BaseUtils.println("Quit  -> q");

            String option = BaseUtils.readText("Insert option: ");

            switch (option){
                case "1" -> crud();
                case "2" -> settings();
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
        switch (option){
            case "1"-> studentUI.changeUserName();
            case "2" -> studentUI.changePassword();
            default -> BaseUtils.println("Wrong option");
        }
    }

    private static void crud() {

    }
}