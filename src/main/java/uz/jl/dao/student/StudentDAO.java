package uz.jl.dao.student;


import uz.jl.dao.GenericDAO;
import uz.jl.domains.auth.StudentEntity;


public class StudentDAO extends GenericDAO<StudentEntity, Long> {

    private static StudentDAO instance;

    public static StudentDAO getInstance() {
        if (instance == null) {
            instance = new StudentDAO();
        }
        return instance;
    }

}