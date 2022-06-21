package uz.jl.ui;

import uz.jl.vo.http.Response;

public class AuthUI {
    public static void main(String[] args) {
        Response.errorBuilder()
                .friendlyMessage("Error happen")
                .developerMessage("Bro take a look db")
                .status(500)
                .build();
    }
}
