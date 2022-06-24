package uz.jl.ui;

import uz.jl.BaseUtils;
import uz.jl.Colors;
import uz.jl.configs.ApplicationContextHolder;
import uz.jl.enums.QuestionStatus;
import uz.jl.service.SubjectService;
import uz.jl.service.VariantService;
import uz.jl.vo.answer.AnswerVO;
import uz.jl.vo.auth.Session;
import uz.jl.vo.http.DataVO;
import uz.jl.vo.http.Response;
import uz.jl.vo.question.QuestionVO;
import uz.jl.vo.subject.SubjectVO;
import uz.jl.vo.variant.VariantVO;

import java.util.List;
import java.util.Objects;
public class StudentUI {

    private static final StudentUI studentUI = new StudentUI();
    private SubjectService subjectService = ApplicationContextHolder.getBean(SubjectService.class);

    private VariantService variantService = ApplicationContextHolder.getBean(VariantService.class);

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

    }

    private void showHistory() {
        showVariantDetails();

        // TODO: 6/23/22 show list of variants
        // TODO: 6/23/22 ask id of the one of variants and show variant in details
    }

    private void showVariantLists() {
        Response<DataVO<List<VariantVO>>> allVariants = variantService.getAll(null);
        if (allVariants.getStatus() != 200){
            print_response(allVariants);
        }else {
            for (VariantVO variantVO : allVariants.getData().getBody()) {
                BaseUtils.println("Variant id: "+variantVO.id+", time: "+variantVO.getCreatedAt()+", status: "+variantVO.getStatus()+", Number of correct answers: "+variantVO.getNumberOfRightAnswers());
            }
        }

    }

    private void showVariantDetails() {
        showVariantLists();
        BaseUtils.println("show detailed -1");
        BaseUtils.println("go back  -2");

        String choice = BaseUtils.readText("choice ? ");
        switch (choice){
            case "2"-> {
                return;
            }
        }
        Long variantId=Long.valueOf(BaseUtils.readText("Enter variantId: "));
        Response<DataVO<List<VariantVO>>> allVariants = variantService.getAll(variantId);
        char c;
        if (allVariants.getStatus() != 200){
            print_response(allVariants);
        }else {
            for (VariantVO variantVO : allVariants.getData().getBody()) {
                for (QuestionVO question : variantVO.getQuestions()) {
                    BaseUtils.println("");
                    BaseUtils.println("Question id: "+question.id+", Body: "+question.getBody()+", status: "+question.getStatus()+", subject: "+question.getSubject().getTitle());
                    for (AnswerVO answer : question.getAnswers()) {
                        BaseUtils.println("Answer: "+answer.getBody()+", status: "+answer.getStatus());
                    }
                }
            }
        }
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

    public static void print_response(Response response) {
        String color = response.getStatus() != 200 ? Colors.RED : Colors.GREEN;
        BaseUtils.println(BaseUtils.gson.toJson(response), color);
    }
}
