package uz.jl.ui;

import uz.jl.BaseUtils;
import uz.jl.Colors;
import uz.jl.configs.ApplicationContextHolder;
import uz.jl.domains.QA.QuestionEntity;
import uz.jl.enums.AuthRole;
import uz.jl.enums.QuestionStatus;
import uz.jl.service.QuestionService;
import uz.jl.service.StudentService;
import uz.jl.service.auth.AuthUserService;
import uz.jl.vo.auth.AuthUserVO;
import uz.jl.vo.http.DataVO;
import uz.jl.vo.http.Response;

import java.util.List;

/**
 * @author "Otajonov Dilshodbek
 * @since 6/22/22 2:31 PM (Wednesday)
 * QuizApp/IntelliJ IDEA
 */
public class AdminUI {


    static StudentService studentService = ApplicationContextHolder.getBean(StudentService.class);
    static AuthUserService authUserService = ApplicationContextHolder.getBean(AuthUserService.class);
    static QuestionService questionService = ApplicationContextHolder.getBean(QuestionService.class);


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
            case "2" -> showTeacherList();
            case "3" -> showQuestionList();
        }

    }

    private static void showQuestionList() {
        BaseUtils.println("Just a list of questions on the subject -> 1");
        BaseUtils.println("Selection by parameter -> 2");
        String choice = BaseUtils.readText("choice ? ");
        switch (choice) {
            case "1" -> listSubject();
            case "2" -> selectionByParameter();
        }
    }

    private static void listSubject() {
        String subject = BaseUtils.readText("Enter subject name: ");
        Response<DataVO<List<QuestionEntity>>> responseList = questionService.getAll(subject, null);

        if (responseList.getStatus().equals(200)) {
            for (QuestionEntity questionVO : responseList.getData().getBody()) {
                BaseUtils.println(questionVO);
            }
        } else print_response(responseList);
    }

    private static void selectionByParameter() {
        String name = BaseUtils.readText("Enter subject name: ");
        BaseUtils.println("1.EASY 2.MEDIUM 3.HARD");
        String choice = BaseUtils.readText("choice ? ");
        QuestionStatus level = null;
        switch (choice) {
            case "1" -> level = QuestionStatus.EASY;
            case "2" -> level = QuestionStatus.MEDIUM;
            case "3" -> level = QuestionStatus.HARD;
        }

        Response<DataVO<List<QuestionEntity>>> responseList = questionService.getAll(name, level);
        if (responseList.getStatus().equals(200)) {
            for (QuestionEntity questionEntity : responseList.getData().getBody()) {
                BaseUtils.println(questionEntity);
            }
        }
    }


    private static void showTeacherList() {

    }

    private static void showStudentList() {
        Response<DataVO<List<AuthUserVO>>> responseList = authUserService.getAll(AuthRole.STUDENT);
        if (responseList.getStatus().equals(200)) {
            for (AuthUserVO authUserVO : responseList.getData().getBody()) {
                BaseUtils.println(authUserVO);
            }
        } else print_response(responseList);
    }

    public static void print_response(Response response) {
        String color = response.getStatus() != 200 ? Colors.RED : Colors.GREEN;
        BaseUtils.println(BaseUtils.gson.toJson(response), color);
    }
}
