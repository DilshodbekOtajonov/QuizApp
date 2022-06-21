package uz.jl.utils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import uz.jl.configs.PasswordConfigurer;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class BaseUtils {
    private static BaseUtils instance;

    public String encode(String rawPassword) {
        return PasswordConfigurer.encode(rawPassword);
    }

    public boolean matchPassword(String rawPassword, String encodedPassword) {
        return PasswordConfigurer.matchPassword(rawPassword, encodedPassword);
    }

    public static BaseUtils getInstance() {
        if (instance == null) {
            instance = new BaseUtils();
        }
        return instance;
    }
}
