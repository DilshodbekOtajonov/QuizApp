package uz.jl.ui;

import uz.jl.BaseUtils;
import uz.jl.configs.ApplicationContextHolder;
import uz.jl.enums.QuestionStatus;
import uz.jl.service.SubjectService;
import uz.jl.service.auth.AuthUserService;
import uz.jl.vo.auth.Session;
import uz.jl.vo.http.DataVO;
import uz.jl.vo.http.Response;
import uz.jl.vo.subject.SubjectVO;

import java.util.List;
import java.util.Objects;

/**
 * @author "Otajonov Dilshodbek
 * @since 6/22/22 2:30 PM (Wednesday)
 * QuizApp/IntelliJ IDEA
 */
public class StudentUI {

    private static final StudentUI studentUI = new StudentUI();
    private SubjectService subjectService = ApplicationContextHolder.getBean(SubjectService.class);
    private AuthUserService authUserService = ApplicationContextHolder.getBean(AuthUserService.class);

    public static void main(String[] args) {
        if (Objects.nonNull(Session.sessionUser)) {
            System.out.println("=================User Page=================");
            BaseUtils.println("Do test -> 1");
            BaseUtils.println("Show history ->2");
            BaseUtils.println("Change auth info -> 3");
            BaseUtils.println("logout -> 4");
            BaseUtils.println("Quit -> q");

            String choice = BaseUtils.readText("choice ? ");
            switch (choice) {
                case "1" -> studentUI.doTest();
                case "2" -> studentUI.showHistory();
                case "3" -> studentUI.updateAuthInfo();
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

    private void updateAuthInfo() {
        // TODO: 6/23/22 ask change password
        // TODO: 6/23/22 ask change username

       BaseUtils.println("1.Change username");
       BaseUtils.println("1.Change password");
        String option = BaseUtils.readText("Choose option: ");
        switch (option){
            case "1"->changeUserName();
            case "2"->changePassword();
            default -> BaseUtils.println("Wrong option");
        }

    }

    public void changePassword() {

        String newPassword = BaseUtils.readText("Insert new password: ");

        authUserService.changePassword(newPassword);
    }

    public void changeUserName() {
        String newUsername = BaseUtils.readText("Insert new username: ");


        authUserService.changeUsername(newUsername);

    }

    private void showHistory() {
        // TODO: 6/23/22 show list of variants
        // TODO: 6/23/22 ask id of the one of variants and show variant in details
    }

    private void doTest() {
        // TODO: 6/23/22 make student select difficulty
        // TODO: 6/23/22 student enters number of questions
        // TODO: 6/23/22 add timer
        // TODO: 6/23/22 ask start test
        // TODO: 6/23/22 show result at the end
        Response<DataVO<List<SubjectVO>>> all = subjectService.getAll();
        for (SubjectVO subjectVO : all.getData().getBody()) {
            // TODO: 6/23/22 subject list is done now add switch case 
        }
        for (QuestionStatus value : QuestionStatus.values()) {
            // TODO: 6/23/22 make student  
        }
    }
}
