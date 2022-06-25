package uz.jl.ui;

import uz.jl.BaseUtils;
import uz.jl.Colors;
import uz.jl.configs.ApplicationContextHolder;
import uz.jl.enums.AuthRole;
import uz.jl.service.StudentService;
import uz.jl.service.TeacherService;
import uz.jl.service.auth.AuthUserService;
import uz.jl.vo.auth.Session;
import uz.jl.vo.http.DataVO;
import uz.jl.vo.http.Response;
import uz.jl.vo.student.StudentCreateVO;
import uz.jl.vo.teacher.TeacherCreateVO;

/**
 * @author "Otajonov Dilshodbek
 * @since 6/24/22 3:36 PM (Friday)
 * QuizApp/IntelliJ IDEA
 */
public class UserUI {

    private static final AuthUserService authUserService = ApplicationContextHolder.getBean(AuthUserService.class);
    private static final StudentService studentService = ApplicationContextHolder.getBean(StudentService.class);
    private static final TeacherService teacherService = ApplicationContextHolder.getBean(TeacherService.class);

    public static void main(String[] args) {
        BaseUtils.println("1 -> Login as Student");
        BaseUtils.println("2 -> Login as Teacher");
        BaseUtils.println("l -> LogOut");
        BaseUtils.println("q -> Quit");
        String choice = BaseUtils.readText("choice ? ");

        switch (choice) {
            case "1" -> setUserAsStudent();
            case "2" -> setUserAsTeacher();
            case "l" -> Session.setSessionUser(null);
            case "q" -> {
                BaseUtils.println("Bye");
                System.exit(0);
            }
            default -> BaseUtils.println("Invalid choice");
        }
    }

    private static void setUserAsTeacher() {
        TeacherCreateVO vo = TeacherCreateVO.builder()
                .name(BaseUtils.readText("Name ? "))
                .surname(BaseUtils.readText("Surname ? "))
                .build();



        Response<DataVO<Long>> response = teacherService.create(vo);
        print_response(response);

        authUserService.setRole(Session.sessionUser.getId(), AuthRole.TEACHER);
        Session.sessionUser.setRole(AuthRole.TEACHER);
    }

    private static void setUserAsStudent() {
        StudentCreateVO vo = StudentCreateVO.builder()
                .name(BaseUtils.readText("Name ? "))
                .surname(BaseUtils.readText("Surname ? "))
                .build();
        Response<DataVO<Long>> response = studentService.create(vo);
        print_response(response);

        authUserService.setRole(Session.sessionUser.getId(), AuthRole.STUDENT);
        Session.sessionUser.setRole(AuthRole.STUDENT);
    }

    public static void print_response(Response response) {

        String color = response.getStatus() != 200 ? Colors.RED : Colors.GREEN;
        BaseUtils.println(BaseUtils.gson.toJson(response), color);
    }
}

