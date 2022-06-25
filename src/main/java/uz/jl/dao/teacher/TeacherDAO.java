package uz.jl.dao.teacher;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import uz.jl.dao.GenericDAO;

import uz.jl.domains.auth.TeacherEntity;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class TeacherDAO extends GenericDAO<TeacherEntity, Long> {


    private static TeacherDAO instance;


    public static TeacherDAO getInstance() {
        if (instance == null) {
            instance = new TeacherDAO();
        }
        return instance;
    }


}
