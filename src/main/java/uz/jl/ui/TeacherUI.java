package uz.jl.ui;

import uz.jl.BaseUtils;
import uz.jl.Colors;
import uz.jl.configs.ApplicationContextHolder;
import uz.jl.domains.subject.SubjectEntity;
import uz.jl.enums.AnswerStatus;
import uz.jl.enums.QuestionStatus;
import uz.jl.service.QuestionService;
import uz.jl.service.SubjectService;
import uz.jl.service.TeacherService;
import uz.jl.vo.answer.AnswerCreateVO;
import uz.jl.vo.auth.Session;
import uz.jl.vo.http.DataVO;
import uz.jl.vo.http.Response;
import uz.jl.vo.question.QuestionCreateVO;
import uz.jl.vo.question.QuestionUpdateVO;
import uz.jl.vo.subject.SubjectVO;
import uz.jl.vo.teacher.TeacherVO;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @author "Otajonov Dilshodbek
 * @since 6/23/22 8:08 AM (Thursday)
 * QuizApp/IntelliJ IDEA
 */
public class TeacherUI {

    private static final SubjectService subjectService = ApplicationContextHolder.getBean(SubjectService.class);
    private static final TeacherService teacherService = ApplicationContextHolder.getBean(TeacherService.class);
    private static final QuestionService questionService = ApplicationContextHolder.getBean(QuestionService.class);

    public static void main(String[] args) {

        if (Objects.nonNull(Session.sessionUser)) {

            BaseUtils.println("CRUD questions -> 1 ");
            BaseUtils.println("Settings  -> 2");
            BaseUtils.println("add subject  -> 3");
            BaseUtils.println("Show my subjects  -> 4");
            BaseUtils.println("Log Out  -> l");
            BaseUtils.println("Quit  -> q");

            String option = BaseUtils.readText("Insert option: ");

            switch (option) {
                case "1" -> crud();
                case "2" -> settings();
                case "3" -> addSubject();
                case "4" -> showMySubjects();
                case "l" -> Session.setSessionUser(null);
                case "q" -> System.exit(0);
                default -> BaseUtils.println("Wrong option");
            }
            main(args);
        }
    }

    private static void showMySubjects() {
        Response<DataVO<List<SubjectEntity>>> subjectListResponse = teacherService.getSubjectList(Session.sessionUser.getId());
        if (subjectListResponse.getStatus() != 200) {
            print_response(subjectListResponse);
            return;
        }
        List<SubjectEntity> subjectList = subjectListResponse.getData().getBody();
        BaseUtils.println("Your subjects: ", Colors.PURPLE);
        for (SubjectEntity subject : subjectList) {
            BaseUtils.println(subject.getTitle(), Colors.PURPLE);
        }
    }

    private static void addSubject() {

        showMySubjects();

        Response<DataVO<List<SubjectVO>>> subjectListResponse = subjectService.getAll();

        if (subjectListResponse.getStatus() != 200) {
            print_response(subjectListResponse);
            return;
        }
        List<SubjectVO> subjectList = subjectListResponse.getData().getBody();

        BaseUtils.println("Choose subject", Colors.YELLOW);
        for (SubjectVO subjectVO : subjectList) {
            BaseUtils.println(subjectVO, Colors.YELLOW);
        }

        TeacherVO teacherVO = TeacherVO.builder()
                .id(Session.sessionUser.getId())
                .subjectList(new ArrayList<>())
                .build();

        while (true) {
            String subjectName = BaseUtils.readText("enter name: ");
            Response<DataVO<SubjectVO>> subjectResponse = subjectService.get(subjectName);
            if (subjectResponse.getStatus() != 200) {
                print_response(subjectResponse);
                return;
            }
            teacherVO.getSubjectList().add(subjectResponse.getData().getBody());

            BaseUtils.println("Continue -> 1");
            BaseUtils.println("Done  -> default");
            String choice = BaseUtils.readText("choice ? ");

            if (!choice.equals("1"))
                break;
        }
        Response<DataVO<Void>> dataVOResponse = teacherService.addSubjectList(teacherVO);

        if (dataVOResponse.getStatus() != 200)
            print_response(dataVOResponse);
        else BaseUtils.println("Done");

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


    public static void print_response(Response response) {
        String color = response.getStatus() != 200 ? Colors.RED : Colors.GREEN;
        BaseUtils.println(BaseUtils.gson.toJson(response), color);
    }


    private static void crud() {
        BaseUtils.println("Create questions -> 1");
        BaseUtils.println("Update question  -> 2");
        BaseUtils.println("Go back          -> 3");
        BaseUtils.println("Log out          -> l");
        BaseUtils.println("Quit             -> q");

        String option = BaseUtils.readText("Insert option: ");
        switch (option) {
            case "1" -> questionCreate();
            case "2" -> updateQuestion();
            case "l" -> Session.setSessionUser(null);
            case "q" -> {
                BaseUtils.println("Bye");
                System.exit(0);
            }
        }
    }


    public static void updateQuestion() {
        Long questionId = Long.valueOf(BaseUtils.readText("Enter question id: "));


        BaseUtils.println("Update level   -> 1");
        BaseUtils.println("Update body    -> 2");
        BaseUtils.println("Back           -> 0");


        String option = BaseUtils.readText("Insert option: ");
        switch (option) {
            case "1" -> updateLevel(questionId);
            case "2" -> updateBody(questionId);
            case "0" -> {
            }
            default -> BaseUtils.println("Invalid option");
        }
    }


    private static void updateBody(Long questionId) {

        String newBody = BaseUtils.readText("Enter body: ");
        Response<DataVO<Void>> update = questionService.update(QuestionUpdateVO.childBuilder()
                .id(questionId)
                .body(newBody)
                .build());

        if (update.getStatus() != 200)
            print_response(update);
        else BaseUtils.println("Done");


    }

    private static void updateLevel(Long questionId) {
        QuestionStatus questionStatus = null;

        BaseUtils.println("EASY   -> 1");
        BaseUtils.println("MEDIUM -> 2");
        BaseUtils.println("HARD   -> 3");

        String option = BaseUtils.readText("? ");
        switch (option) {
            case "1" -> questionStatus = QuestionStatus.EASY;
            case "2" -> questionStatus = QuestionStatus.MEDIUM;
            case "3" -> questionStatus = QuestionStatus.HARD;
        }


        Response<DataVO<Void>> update = questionService.update(QuestionUpdateVO.childBuilder()
                .id(questionId)
                .status(questionStatus)
                .build());

        if (update.getStatus() != 200)
            print_response(update);
        else BaseUtils.println("Done");
    }

    private static void questionCreate() {

        Response<DataVO<List<SubjectEntity>>> subjectListResponse = teacherService.getSubjectList(Session.sessionUser.getId());
        if (subjectListResponse.getStatus() != 200) {
            print_response(subjectListResponse);
            return;
        }

        List<SubjectEntity> subjectList = subjectListResponse.getData().getBody();
        BaseUtils.println("Your subjects: ", Colors.PURPLE);
        for (SubjectEntity subject : subjectList) {
            BaseUtils.println(subject.getTitle(), Colors.PURPLE);
        }

        boolean flag = false;
        String option = BaseUtils.readText("Enter subject name: ");
        for (SubjectEntity subject : subjectList) {
            if (Objects.equals(subject.getTitle(), option)) {
                flag = true;
                break;
            }
        }

        if (flag) {
            QuestionCreateVO vo = QuestionCreateVO.builder()
                    .body(BaseUtils.readText("body ? "))
                    .createdBy(Session.sessionUser.getId())
                    .subjectName(option)
                    .createdBy(Session.sessionUser.getId())
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

                default -> {
                    BaseUtils.println("Invalid choice");
                    return;
                }

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

        } else BaseUtils.println("Subject not found '%s' ".formatted(option));

    }
}
