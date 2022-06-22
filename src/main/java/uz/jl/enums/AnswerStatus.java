package uz.jl.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author "Otajonov Dilshodbek
 * @since 6/22/22 2:40 PM (Wednesday)
 * QuizApp/IntelliJ IDEA
 */
@AllArgsConstructor
@Getter
public enum AnswerStatus {

    RIGHT(1),
    WRONG(0);


    private final int priority;

}
