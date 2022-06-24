package uz.jl.service;

import lombok.NonNull;
import uz.jl.configs.ApplicationContextHolder;
import uz.jl.dao.AbstractDAO;
import uz.jl.dao.auth.AuthUserDAO;
import uz.jl.dao.qa.QuestionDAO;
import uz.jl.dao.subject.SubjectDAO;
import uz.jl.dao.variant.VariantDAO;
import uz.jl.domains.QA.AnswerEntity;
import uz.jl.domains.QA.QuestionEntity;
import uz.jl.domains.QA.VariantEntity;
import uz.jl.domains.auth.AuthUser;
import uz.jl.domains.subject.SubjectEntity;
import uz.jl.enums.QuestionStatus;
import uz.jl.utils.BaseUtils;
import uz.jl.utils.validators.variantValidators.VariantValidator;
import uz.jl.vo.answer.AnswerVO;
import uz.jl.vo.http.AppErrorVO;
import uz.jl.vo.http.DataVO;
import uz.jl.vo.http.Response;
import uz.jl.vo.question.QuestionVO;
import uz.jl.vo.variant.VariantCreateVO;
import uz.jl.vo.variant.VariantUpdateVO;
import uz.jl.vo.variant.VariantVO;

import java.util.ArrayList;
import java.util.List;

/**
 * @author "Otajonov Dilshodbek
 * @since 6/23/22 8:50 AM (Thursday)
 * QuizApp/IntelliJ IDEA
 */
public class VariantService extends AbstractDAO<VariantDAO> implements GenericCRUDService<
        VariantVO,
        VariantCreateVO,
        VariantUpdateVO,
        Long> {
    private static VariantService instance;
    VariantValidator validator = ApplicationContextHolder.getBean(VariantValidator.class);
    private static AuthUserDAO authUserDAO = ApplicationContextHolder.getBean(AuthUserDAO.class);
    private static QuestionDAO questionDAO = ApplicationContextHolder.getBean(QuestionDAO.class);
    private static SubjectDAO subjectDAO = ApplicationContextHolder.getBean(SubjectDAO.class);

    private VariantService() {
        super(
                ApplicationContextHolder.getBean(VariantDAO.class),
                ApplicationContextHolder.getBean(BaseUtils.class));
    }

    public static VariantService getInstance() {
        if (instance == null) {
            instance = new VariantService();
        }
        return instance;
    }

    @NonNull
    public Response<DataVO<Long>> create(@NonNull VariantCreateVO vo) {
        return null;
    }

    public Response<DataVO<VariantEntity>> getVariant(@NonNull VariantCreateVO vo) {
        try {
            validator.validOnCreate(vo);
            SubjectEntity subjectEntity = subjectDAO.findByName(vo.getSubjectName());
            AuthUser authUser = authUserDAO.findById(vo.getUserId());

            List<QuestionEntity> questionEntitiesList = questionDAO.findAllBySubjectIdAndLevel(subjectEntity.getId(), vo.getLevel(), vo.getNumberOfQuestions());
            VariantEntity variantEntity = VariantEntity.childBuilder()
                    .questions(questionEntitiesList)
                    .user(authUser)
                    .numberOfRightAnswers(vo.getNumberOfQuestions())
                    .build();

            VariantEntity save = dao.save(variantEntity);


            return new Response<>(new DataVO<>(save),200);
        } catch (Exception e) {
            e.printStackTrace();
            return new Response<>(new DataVO<>(AppErrorVO.builder()
                    .friendlyMessage(e.getMessage())
                    .build()),400);
        }
    }


    @Override
    public Response<DataVO<Void>> update(@NonNull VariantUpdateVO vo) {
        return null;
    }

    @Override
    public Response<DataVO<Void>> delete(@NonNull Long aLong) {
        return null;
    }

    @Override
    public Response<DataVO<VariantVO>> get(@NonNull Long aLong) {
        return null;
    }

    @Override
    public Response<DataVO<List<VariantVO>>> getAll() {
        return null;
    }
}
