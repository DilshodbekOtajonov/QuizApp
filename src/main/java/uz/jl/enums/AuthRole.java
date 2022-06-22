package uz.jl.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum AuthRole {

    ADMIN(100),
    TEACHER(50),
    STUDENT(10);

    private final int priority;
}
