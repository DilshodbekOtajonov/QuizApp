package uz.jl.ui;

import uz.jl.BaseUtils;
import uz.jl.Colors;
import uz.jl.configs.ApplicationContextHolder;
import uz.jl.service.SubjectService;
import uz.jl.service.TeacherService;
import uz.jl.vo.auth.Session;
import uz.jl.vo.http.DataVO;
import uz.jl.vo.http.Response;
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

    public static void main(String[] args) {

        if (Objects.nonNull(Session.sessionUser)) {
            BaseUtils.println("CRUD questions -> 1 ");
            BaseUtils.println("Settings  -> 2");
            BaseUtils.println("add subject  -> 3");
            BaseUtils.println("Log Out  -> l");
            BaseUtils.println("Quit  -> q");

            String option = BaseUtils.readText("Insert option: ");

            switch (option) {
                case "1" -> crud();
                case "2" -> settings();
                case "3" -> addSubject();
                case "l" -> Session.setSessionUser(null);
                case "q" -> System.exit(0);
                default -> BaseUtils.println("Wrong option");
            }
            main(args);
        }
    }

    private static void addSubject() {
        Response<DataVO<List<SubjectVO>>> subjectListResponse = subjectService.getAll();

        if (subjectListResponse.getStatus() != 200) {
            print_response(subjectListResponse);
            return;
        }
        List<SubjectVO> subjectList = subjectListResponse.getData().getBody();

        for (SubjectVO subjectVO : subjectList) {
            BaseUtils.println(subjectVO, Colors.YELLOW);
        }

        TeacherVO teacherVO = TeacherVO.builder()
                .id(Session.sessionUser.getId())
                .subjectList(new ArrayList<>())
                .build();
        label:
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

            switch (choice) {
                case "1" -> {
                    continue;
                }
                default -> {
                    break label;
                }
            }
        }

        Response<DataVO<Void>> dataVOResponse = teacherService.addSubjectList(teacherVO);

        print_response(dataVOResponse);


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
        BaseUtils.println("  -> 2");
        BaseUtils.println("Quit  -> q");
    }

    public static void print_response(Response response) {
        String color = response.getStatus() != 200 ? Colors.RED : Colors.GREEN;
        BaseUtils.println(BaseUtils.gson.toJson(response), color);
    }
}
