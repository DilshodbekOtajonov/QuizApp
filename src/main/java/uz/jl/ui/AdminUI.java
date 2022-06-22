package uz.jl.ui;

import uz.jl.BaseUtils;
import uz.jl.configs.ApplicationContextHolder;
import uz.jl.service.StudentService;

/**
 * @author "Otajonov Dilshodbek
 * @since 6/22/22 2:31 PM (Wednesday)
 * QuizApp/IntelliJ IDEA
 */
public class AdminUI {

    static StudentService studentService = ApplicationContextHolder.getBean(StudentService.class);

    public static void main(String[] args) {
        System.out.println("=================Admin page==================");
        BaseUtils.println("Show Student List  -> 1");
        BaseUtils.println("Show Teacher List  -> 2");
        BaseUtils.println("Show Question List  -> 3");
        BaseUtils.println("Question create -> 4");
        BaseUtils.println("Question update  -> 5");
        BaseUtils.println("Question delete  -> 6");

        String choice = BaseUtils.readText("choice ? ");
        switch (choice) {
            case "1" -> showStudentList();
        }

    }

    private static void showStudentList() {

    }
}
