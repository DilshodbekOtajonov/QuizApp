package uz.jl.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author "Otajonov Dilshodbek
 * @since 6/22/22 3:05 PM (Wednesday)
 * QuizApp/IntelliJ IDEA
 */
@AllArgsConstructor
@Getter
public enum QuestionStatus {

    EASY(10),
    MEDIUM(50),
    HARD(90);

    private Integer priority;
}
