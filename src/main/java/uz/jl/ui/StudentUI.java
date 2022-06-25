package uz.jl.ui;

import uz.jl.BaseUtils;
import uz.jl.Colors;
import uz.jl.configs.ApplicationContextHolder;
import uz.jl.domains.QA.AnswerEntity;
import uz.jl.domains.QA.QuestionEntity;
import uz.jl.domains.QA.VariantEntity;
import uz.jl.enums.AnswerStatus;
import uz.jl.enums.QuestionStatus;
import uz.jl.service.QuestionService;
import uz.jl.service.SubjectService;
import uz.jl.service.VariantService;
import uz.jl.service.auth.AuthUserService;
import uz.jl.vo.answer.AnswerVO;
import uz.jl.vo.auth.AuthUserPasswordResetVO;
import uz.jl.vo.auth.AuthUserVO;
import uz.jl.vo.auth.Session;
import uz.jl.vo.http.DataVO;
import uz.jl.vo.http.Response;
import uz.jl.vo.question.QuestionVO;
import uz.jl.vo.subject.SubjectVO;
import uz.jl.vo.variant.VariantCreateVO;
import uz.jl.vo.variant.VariantVO;

import java.time.LocalTime;
import java.util.List;
import java.util.Objects;

/**
 * @author "Otajonov Dilshodbek
 * @since 6/22/22 2:30 PM (Wednesday)
 * QuizApp/IntelliJ IDEA
 */
public class StudentUI {

    private static final StudentUI studentUI = new StudentUI();
    private static final AuthUserService authUserService = ApplicationContextHolder.getBean(AuthUserService.class);
    private static final SubjectService subjectService = ApplicationContextHolder.getBean(SubjectService.class);
    private static final VariantService variantService = ApplicationContextHolder.getBean(VariantService.class);

    public static void main(String[] args) {
        if (Objects.nonNull(Session.sessionUser)) {
            System.out.println("=================Student Page=================");
            BaseUtils.println("Do test -> 1");
            BaseUtils.println("Show history ->2");
            BaseUtils.println("Change auth info -> 3");
            BaseUtils.println("Delete account-> 4");
            BaseUtils.println("logout -> l");
            BaseUtils.println("Quit -> q");

            String choice = BaseUtils.readText("choice ? ");
            switch (choice) {
                case "1" -> studentUI.doTest();
                case "2" -> studentUI.showHistory();
                case "3" -> studentUI.updateAuthInfo();
                case "4" -> studentUI.deletedStudent();
                case "l" -> Session.setSessionUser(null);
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

        BaseUtils.println("1.Change username");
        BaseUtils.println("2.Change password");
        BaseUtils.println("default go back");
        String option = BaseUtils.readText("Choose option: ");
        switch (option) {
            case "1" -> changeUserName();
            case "2" -> changePassword();
            default -> BaseUtils.println("Wrong option", Colors.RED);
        }
    }

    public static void changePassword() {
        AuthUserPasswordResetVO resetVO = AuthUserPasswordResetVO.builder()
                .oldPassword(BaseUtils.readText("Insert old password: "))
                .newPassword(BaseUtils.readText("Insert new password: "))
                .confirmNewPassword(BaseUtils.readText("confirm new password: "))
                .build();

        Response<DataVO<Void>> response = authUserService.changePassword(resetVO);
        if (response.getStatus() != 200)
            print_response(response);
        else BaseUtils.println("Done", Colors.GREEN);

    }

    public static void changeUserName() {
        String newUsername = BaseUtils.readText("Insert new username: ");
        Response<DataVO<Void>> response = authUserService.changeUsername(newUsername);
        if (response.getStatus() != 200)
            print_response(response);
        else BaseUtils.println("Done", Colors.GREEN);
    }


    private void showHistory() {

        Response<DataVO<List<VariantVO>>> allVariants = variantService.getAllByStudentId(Session.sessionUser.getId());
        if (allVariants.getStatus() != 200) {
            print_response(allVariants);
            return;
        } else {
            for (VariantVO variantVO : allVariants.getData().getBody()) {
                BaseUtils.println("Variant id: " + variantVO.id + ", time: " + variantVO.getCreatedAt() + ", status: " + variantVO.getStatus() + ", Number of correct answers: " + variantVO.getNumberOfRightAnswers());
            }
        }

        BaseUtils.println("show detailed -1");
        BaseUtils.println("go back  -2");

        String choice = BaseUtils.readText("choice ? ");
        switch (choice) {

            case "1" -> BaseUtils.println("");

            case "2" -> {
                return;
            }
            default -> {
                BaseUtils.println("Invalid option");
                System.exit(0);
            }
        }
        Long variantId = Long.valueOf(BaseUtils.readText("Enter variantId: "));
        Response<DataVO<VariantVO>> voResponse = variantService.get(variantId);
        if (voResponse.getStatus() != 200) {
            print_response(voResponse);
        } else {
            for (QuestionVO question : voResponse.getData().getBody().getQuestions()) {
                BaseUtils.println("");
                BaseUtils.println("Question id: " + question.id + ", Body: " + question.getBody() + ", status: " + question.getStatus() + ", subject: " + question.getSubject().getTitle());
                for (AnswerVO answer : question.getAnswers()) {
                    BaseUtils.println("Answer: " + answer.getBody() + ", status: " + answer.getStatus());
                }
            }
        }
    }

    private void doTest() {
        try {
            VariantEntity variant = createVariant();
            BaseUtils.println("\n\nStart 1", Colors.YELLOW);
            BaseUtils.println("cancel any key", Colors.YELLOW);
            String choice = BaseUtils.readText("choice ? ");
            switch (choice) {
                case "1" -> BaseUtils.println("Test started");
                default -> {
                    BaseUtils.println("Test canceled");
                    return;
                }
            }
            LocalTime startTime = LocalTime.now();
            int numberOfQuestion = variant.getQuestions().size();
            LocalTime endTime = startTime.plusMinutes(numberOfQuestion);
            BaseUtils.println("Start time " + startTime);
            BaseUtils.println("End time " + endTime);
            BaseUtils.println("You have %s minutes\n".formatted(numberOfQuestion));
            Integer numberOfRightAnswers = 0;
            for (QuestionEntity question : variant.getQuestions()) {

                if (LocalTime.now().isAfter(endTime))
                    break;
                BaseUtils.println(question.getBody(), Colors.PURPLE);

                AnswerEntity rightAnswer = null;
                List<AnswerEntity> answers = question.getAnswers();
                for (AnswerEntity answer : answers) {
                    System.out.println(answer.getBody());
                    if (answer.getStatus().equals(AnswerStatus.RIGHT))
                        rightAnswer = answer;
                }

                String studentAnswer = BaseUtils.readText("Your answer : ");
                if (studentAnswer.equalsIgnoreCase(rightAnswer.getBody()))
                    numberOfRightAnswers++;

            }

            BaseUtils.println("Your result: " + numberOfRightAnswers);


            variant.setNumberOfRightAnswers(numberOfRightAnswers);
            variant.setCompleted(true);
            variantService.UpdateVariantEntity(variant);

        } catch (Exception ignored) {
        }

    }

    public static void print_response(Response response) {
        String color = response.getStatus() != 200 ? Colors.RED : Colors.GREEN;
        BaseUtils.println(BaseUtils.gson.toJson(response), color);
    }

    private VariantEntity createVariant() {
        Response<DataVO<List<SubjectVO>>> subjectListResponse = subjectService.getAll();
        if (subjectListResponse.getStatus() != 200) {
            print_response(subjectListResponse);
            throw new RuntimeException();
        }
        BaseUtils.println(subjectListResponse.getData().getBody());
        String subjectName = BaseUtils.readText("Subject name? ");
        BaseUtils.println("1.EASY \n2.MEDIUM \n3.HARD", Colors.PURPLE);
        String choice = BaseUtils.readText("choice ? ");
        QuestionStatus level = null;
        switch (choice) {
            case "1" -> level = QuestionStatus.EASY;
            case "2" -> level = QuestionStatus.MEDIUM;
            case "3" -> level = QuestionStatus.HARD;
            default -> {
                BaseUtils.println("Invalid choice", Colors.RED);
                throw new RuntimeException();
            }
        }
        Integer numberOfQuestions;
        try {
            numberOfQuestions = Integer.valueOf(BaseUtils.readText("number of question ? "));
        } catch (NumberFormatException e) {
            BaseUtils.println("Invalid input: Number should be input", Colors.RED);
            throw new RuntimeException();

        }


        VariantCreateVO variantCreateVO = VariantCreateVO.builder()
                .level(level)
                .subjectName(subjectName)
                .numberOfQuestions(numberOfQuestions)
                .userId(Session.sessionUser.getId())
                .build();

        Response<DataVO<VariantEntity>> variantResponse = variantService.createAndGet(variantCreateVO);
        if (variantResponse.getStatus() != 200) {
            print_response(variantResponse);
            throw new RuntimeException();
        }
        return variantResponse.getData().getBody();

    }

    private void deletedStudent() {
        Response<DataVO<Void>> response = authUserService.deleteUser(
                Session.sessionUser.getId(),
                BaseUtils.readText("password ? ")
        );
        if (response.getStatus() != 200)
            print_response(response);
    }
}
