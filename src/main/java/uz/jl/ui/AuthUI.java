package uz.jl.ui;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import uz.jl.BaseUtils;
import uz.jl.Colors;
import uz.jl.configs.ApplicationContextHolder;
import uz.jl.service.auth.AuthUserService;
import uz.jl.vo.auth.AuthUserCreateVO;
import uz.jl.vo.auth.AuthUserVO;
import uz.jl.vo.auth.Session;
import uz.jl.vo.http.DataVO;
import uz.jl.vo.http.Response;

import java.util.Objects;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class AuthUI {
    private static final AuthUI authUI = new AuthUI();
    AuthUserService service = ApplicationContextHolder.getBean(AuthUserService.class);

    public static void main(String[] args) {

        if (Objects.isNull(Session.sessionUser)) {
            BaseUtils.println("============welcome to Quiz App===========", Colors.BLUE);
            BaseUtils.println("Login -> 1");
            BaseUtils.println("Register -> 2");
            BaseUtils.println("Quit -> q");

            String choice = BaseUtils.readText("choice ? ");

            switch (choice) {
                case "1" -> authUI.login();
                case "2" -> authUI.register();
                case "q" -> {
                    BaseUtils.println("Bye");
                    System.exit(0);
                }
                default -> BaseUtils.println("Invalid choice");
            }
        } else {
            switch (Session.sessionUser.getRole()) {
                case STUDENT -> StudentUI.main(args);
                case ADMIN -> AdminUI.main(args);
                case TEACHER -> TeacherUI.main(args);
                case USER -> UserUI.main(args);
            }
        }
        main(args);

    }

    private void register() {
        AuthUserCreateVO vo = AuthUserCreateVO.builder()
                .username(BaseUtils.readText("username ? "))
                .password(BaseUtils.readText("password ? "))
                .email(BaseUtils.readText("email ? "))
                .build();
        Response<DataVO<Long>> response = service.create(vo);
        print_response(response);
    }

    private void login() {
        Response<DataVO<AuthUserVO>> response = service.login(
                BaseUtils.readText("username ? "),
                BaseUtils.readText("password ? ")
        );
        print_response(response);
    }

    public static void print_response(Response response) {
        String color = response.getStatus() != 200 ? Colors.RED : Colors.GREEN;
        BaseUtils.println(BaseUtils.gson.toJson(response), color);
    }
}
