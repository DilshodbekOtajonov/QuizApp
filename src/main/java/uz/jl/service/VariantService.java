package uz.jl.service;

import lombok.NonNull;
import uz.jl.configs.ApplicationContextHolder;
import uz.jl.dao.AbstractDAO;
import uz.jl.dao.variant.VariantDAO;
import uz.jl.domains.QA.AnswerEntity;
import uz.jl.domains.QA.QuestionEntity;
import uz.jl.domains.QA.VariantEntity;
import uz.jl.domains.subject.SubjectEntity;
import uz.jl.utils.BaseUtils;
import uz.jl.vo.answer.AnswerVO;
import uz.jl.vo.auth.Session;
import uz.jl.vo.http.AppErrorVO;
import uz.jl.vo.http.DataVO;
import uz.jl.vo.http.Response;
import uz.jl.vo.question.QuestionVO;
import uz.jl.vo.subject.SubjectVO;
import uz.jl.vo.variant.VariantCreateVO;
import uz.jl.vo.variant.VariantUpdateVO;
import uz.jl.vo.variant.VariantVO;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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

    private VariantService() {
        super(
                ApplicationContextHolder.getBean(VariantDAO.class),
                ApplicationContextHolder.getBean(BaseUtils.class));
    }

    @Override
    public Response<DataVO<Long>> create(@NonNull VariantCreateVO vo) {
        return null;
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

    public Response<DataVO<List<VariantVO>>> getAll(Long variantId) {
        List<VariantEntity> all;

        if(Objects.isNull(variantId)){
            all=dao.findAll(Session.sessionUser.getId(),variantId);
        }else {
            all=dao.findAllVariantId(Session.sessionUser.getId(),variantId);
        }

        if (all.isEmpty()) {
            return new Response<>(new DataVO<>(AppErrorVO.builder()
                    .friendlyMessage("No variants found").build()),404);
        }

        List<VariantVO> response = new ArrayList<>();
        for (VariantEntity variantEntity : all) {
            VariantVO variantVO = VariantVO.childBuilder()
                    .id(variantEntity.getId())
                    .createdAt(variantEntity.getCreatedAt())
                    .status(variantEntity.getStatus())
                    .numberOfRightAnswers(variantEntity.getNumberOfRightAnswers()).build();

            List<QuestionVO> questionVOList = new ArrayList<>();

            for (QuestionEntity questionEntity : variantEntity.getQuestions()) {
                SubjectEntity subject = questionEntity.getSubject();

                SubjectVO subjectVO = SubjectVO.childBuilder()
                        .id(subject.getId())
                        .title(subject.getTitle())
                        .build();

                QuestionVO questionVO = QuestionVO.childBuilder()
                        .id(questionEntity.getId())
                        .body(questionEntity.getBody())
                        .status(questionEntity.getStatus())
                        .subject(subjectVO)
                        .build();

                List<AnswerVO> answerVOList = new ArrayList<>();
                for (AnswerEntity answer : questionEntity.getAnswers()) {
                    AnswerVO answerVO = AnswerVO.childBuilder()
                            .body(answer.getBody())
                            .id(answer.getId())
                            .status(answer.getStatus())
                            .build();
                    answerVOList.add(answerVO);
                }
                questionVO.setAnswers(answerVOList);
                questionVOList.add(questionVO);
            }
            variantVO.setQuestions(questionVOList);
            response.add(variantVO);
        }
        return new Response<>(new DataVO<>(response), 200);
    }

    public static VariantService getInstance() {
        if (instance == null) {
            instance = new VariantService();
        }
        return instance;
    }
}
