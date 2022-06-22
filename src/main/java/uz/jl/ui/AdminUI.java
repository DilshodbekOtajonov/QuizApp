package uz.jl.ui;

import uz.jl.BaseUtils;
import uz.jl.Colors;
import uz.jl.configs.ApplicationContextHolder;
import uz.jl.enums.AuthRole;
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

    static AuthUserService authUserService = ApplicationContextHolder.getBean(AuthUserService.class);

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

    public static void print_response(Response response) {
        String color = response.getStatus() != 200 ? Colors.RED : Colors.GREEN;
        BaseUtils.println(BaseUtils.gson.toJson(response), color);
    }
}
