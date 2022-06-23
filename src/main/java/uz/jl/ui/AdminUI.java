package uz.jl.ui;

import uz.jl.BaseUtils;
import uz.jl.Colors;
import uz.jl.configs.ApplicationContextHolder;
import uz.jl.enums.AnswerStatus;
import uz.jl.enums.AuthRole;
import uz.jl.enums.QuestionStatus;
import uz.jl.service.QuestionService;
import uz.jl.service.auth.AuthUserService;
import uz.jl.vo.answer.AnswerCreateVO;
import uz.jl.vo.auth.AuthUserVO;
import uz.jl.vo.http.DataVO;
import uz.jl.vo.http.Response;
import uz.jl.vo.question.QuestionCreateVO;

import java.util.Arrays;
import java.util.List;

/**
 * @author "Otajonov Dilshodbek
 * @since 6/22/22 2:31 PM (Wednesday)
 * QuizApp/IntelliJ IDEA
 */
public class AdminUI {

    static AuthUserService authUserService = ApplicationContextHolder.getBean(AuthUserService.class);
    static QuestionService questionService = ApplicationContextHolder.getBean(QuestionService.class);

    public static void main(String[] args) {
        System.out.println("=================Admin page==================");
        BaseUtils.println("Show Student List -> 1");
        BaseUtils.println("Show Teacher List -> 2");
        BaseUtils.println("Show Question List -> 3");
        BaseUtils.println("Question create -> 4");
        BaseUtils.println("Question update -> 5");
        BaseUtils.println("Question delete -> 6");
        BaseUtils.println("Set role to User -> 7");
        String choice = BaseUtils.readText("choice ? ");
        switch (choice) {
            case "1" -> showStudentList();
            case "2" -> showTeacherList();
            case "4" -> questionCreate();
        }
        main(args);
    }

    private static void showStudentList() {
        Response<DataVO<List<AuthUserVO>>> responseList = authUserService.getAll(AuthRole.STUDENT);
        if (responseList.getStatus().equals(200)) {
            for (AuthUserVO authUserVO : responseList.getData().getBody()) {
                BaseUtils.println(authUserVO);
            }
        } else print_response(responseList);
    }

    private static void showTeacherList() {
        Response<DataVO<List<AuthUserVO>>> responseList = authUserService.getAll(AuthRole.TEACHER);
        if (responseList.getStatus().equals(200)) {
            for (AuthUserVO authUserVO : responseList.getData().getBody()) {
                BaseUtils.println(authUserVO);
            }
        } else print_response(responseList);
    }

    private static void questionCreate() {

        QuestionCreateVO vo = QuestionCreateVO.builder()
                .body(BaseUtils.readText("body ? "))
                .build();

        int i = 0;
        System.out.println("Question status:\n");
        for (QuestionStatus value : QuestionStatus.values()) {
            i++;
            System.out.println(i + " " + value);

        }
        String choice = BaseUtils.readText("choice ? ");
        switch (choice) {
            case "1" -> vo.setStatus(QuestionStatus.EASY);
            case "2" -> vo.setStatus(QuestionStatus.MEDIUM);
            case "3" -> vo.setStatus(QuestionStatus.HARD);

            default -> BaseUtils.println("Invalid choice");

        }

        System.out.println("Answer:\n");

        AnswerCreateVO body1 = new AnswerCreateVO();
        body1.setBody(BaseUtils.readText("Enter the correct answer"));
        body1.setStatus(AnswerStatus.RIGHT);

        AnswerCreateVO body2 = new AnswerCreateVO();
        body2.setBody(BaseUtils.readText("Enter the incorrect answer"));
        body2.setStatus(AnswerStatus.WRONG);

        AnswerCreateVO body3 = new AnswerCreateVO();
        body3.setBody(BaseUtils.readText("Enter the incorrect answer"));
        body3.setStatus(AnswerStatus.WRONG);

        AnswerCreateVO body4 = new AnswerCreateVO();
        body4.setBody(BaseUtils.readText("Enter the incorrect answer"));
        body4.setStatus(AnswerStatus.WRONG);

        vo.setAnswers(List.of(body1, body2, body3, body4));
        Response<DataVO<Long>> dataVOResponse = questionService.create(vo);
        print_response(dataVOResponse);

    }

    public static void print_response(Response response) {
        String color = response.getStatus() != 200 ? Colors.RED : Colors.GREEN;
        BaseUtils.println(BaseUtils.gson.toJson(response), color);
    }


}
