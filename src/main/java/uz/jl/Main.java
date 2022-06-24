package uz.jl;

import uz.jl.ui.AuthUI;

import java.util.*;

public class Main {
    public static void main(String[] args) {
//        AuthUI.main(args);
        String[] abcs = {"A", "B", "C", "D"};
        Set<String> abc = new LinkedHashSet<>();
        Random random = new Random();
        int rI = random.nextInt(0, 4);
        abc.add(abcs[rI]);
        abc.addAll(List.of(abcs));
        System.out.println(abc);
    }
}