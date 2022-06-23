package uz.jl.dao.subject;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import uz.jl.dao.GenericDAO;
import uz.jl.domains.SubjectEntity;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class SubjectDAO extends GenericDAO<SubjectEntity,Long> {
    private static SubjectDAO instance;

    public static SubjectDAO getInstance(){
        if (instance == null) {
            instance=new SubjectDAO();
        }
       return instance;
    }
}
