package uz.jl.service;

import lombok.NonNull;
import uz.jl.configs.ApplicationContextHolder;

import uz.jl.dao.AbstractDAO;
import uz.jl.dao.qa.QuestionDAO;
import uz.jl.dao.subject.SubjectDAO;
import uz.jl.domains.QA.AnswerEntity;
import uz.jl.domains.QA.QuestionEntity;
import uz.jl.domains.subject.SubjectEntity;
import uz.jl.enums.QuestionStatus;
import uz.jl.exceptions.ValidationException;
import uz.jl.utils.BaseUtils;

import uz.jl.utils.validators.question.QuestionValidator;
import uz.jl.vo.answer.AnswerCreateVO;
import uz.jl.vo.answer.AnswerVO;
import uz.jl.vo.http.AppErrorVO;
import uz.jl.vo.http.DataVO;
import uz.jl.vo.http.Response;
import uz.jl.vo.question.QuestionCreateVO;
import uz.jl.vo.question.QuestionUpdateVO;
import uz.jl.vo.question.QuestionVO;
import uz.jl.vo.subject.SubjectVO;


import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
;

public class QuestionService extends AbstractDAO<QuestionDAO> implements GenericCRUDService<
        QuestionVO,
        QuestionCreateVO,
        QuestionUpdateVO,
        Long> {

    private static QuestionService instance;

    private static SubjectDAO subjectDAO = ApplicationContextHolder.getBean(SubjectDAO.class);
    private static final SubjectService subjectService = ApplicationContextHolder.getBean(SubjectService.class);
    private final QuestionValidator validator = ApplicationContextHolder.getBean(QuestionValidator.class);

    public QuestionService() {

        super(ApplicationContextHolder.getBean(QuestionDAO.class),
                ApplicationContextHolder.getBean(BaseUtils.class));
    }

    public static QuestionService getInstance() {
        if (instance == null) {
            instance = new QuestionService();
        }
        return instance;
    }

    @Override
    public Response<DataVO<Long>> create(@NonNull QuestionCreateVO vo) {
        try {
            validator.validOnCreate(vo);
            List<AnswerEntity> answerList = new ArrayList<>();
            for (AnswerCreateVO answer : vo.getAnswers()) {
                AnswerEntity build = AnswerEntity.childBuilder()
                        .body(answer.getBody())
                        .status(answer.getStatus())
                        .build();
                answerList.add(build);
            }
            SubjectEntity subjectEntity = subjectDAO.findByName(vo.getSubjectName());
            if (Objects.isNull(subjectEntity))
                return new Response<>(new DataVO<>(AppErrorVO.builder()
                        .friendlyMessage("subject not found by name '%s'".formatted(vo.getSubjectName()))
                        .build()), 500);
            QuestionEntity question = QuestionEntity.childBuilder()
                    .body(vo.getBody())
                    .answers(answerList)
                    .status(vo.getStatus())
                    .createdBy(vo.getCreatedBy())
                    .subject(subjectEntity)
                    .build();

            QuestionEntity save = dao.save(question);
            return new Response<>(new DataVO<>(save.getId()), 200);
        } catch (ValidationException e) {
            return new Response<>(new DataVO<>(AppErrorVO.builder()
                    .friendlyMessage(e.getMessage())
                    .build()), 400);

        }
    }

    @Override
    public Response<DataVO<Void>> update(@NonNull QuestionUpdateVO vo) {
        return null;
    }

    @Override
    public Response<DataVO<Void>> delete(@NonNull Long questionId) {
        QuestionEntity questionEntity = dao.findById(questionId);
        if (Objects.isNull(questionEntity)) {
            return new Response<>(new DataVO<>(AppErrorVO.builder()
                    .friendlyMessage("Question not found by id")
                    .build()), 404);
        }
        try {
            dao.deleteById(questionId);
            return new Response<>(new DataVO<>(null));
        } catch (SQLException e) {
            return new Response<>(new DataVO<>(AppErrorVO.builder()
                    .friendlyMessage("Oops something went wrong")
                    .build()), 500);
        }
    }

    @Override
    public Response<DataVO<QuestionVO>> get(@NonNull Long aLong) {
        return null;
    }

    @Override
    public Response<DataVO<List<QuestionVO>>> getAll() {
        List<QuestionEntity> all = dao.findAll();
        if (all.isEmpty()) {
            return new Response<>(new DataVO<>(AppErrorVO.builder()
                    .friendlyMessage("No question find")
                    .build()), 500);
        }
        List<QuestionVO> response = new ArrayList<>();
        for (QuestionEntity questionEntity : all) {
            SubjectEntity subject = questionEntity.getSubject();

            SubjectVO subjectVO = SubjectVO.childBuilder()
                    .id(subject.getId())
                    .title(subject.getTitle())
                    .build();

            QuestionVO questionVO = QuestionVO.childBuilder()
                    .body(questionEntity.getBody())
                    .id(questionEntity.getId())
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
            response.add(questionVO);
        }

        return new Response<>(new DataVO<>(response), 200);
    }

    public Response<DataVO<List<QuestionVO>>> getAll(String name, QuestionStatus level, Integer numberOfQuestion) {
        try {
            Response<DataVO<SubjectVO>> subjectResponse = subjectService.get(name);
            if (subjectResponse.getStatus() != 200) {
                return new Response<>(new DataVO<>(AppErrorVO.builder()
                        .friendlyMessage("Subject not found")
                        .build()), 400);
            }

            SubjectVO subjectVO = subjectResponse.getData().getBody();


            Long subjectId = subjectVO.getId();
            List<QuestionEntity> resultList;
            if (Objects.isNull(level) && Objects.isNull(numberOfQuestion)) {
                resultList = dao.findAllBySubjectId(subjectId);
            } else if (Objects.isNull(numberOfQuestion)) {
                resultList = dao.findAllBySubjectIdAndLevel(subjectId, level);
            } else {
                resultList = dao.findAllBySubjectIdAndLevel(subjectId, level, numberOfQuestion);
            }

            if (resultList.isEmpty()) {
                return new Response<>(new DataVO<>(AppErrorVO.builder()
                        .friendlyMessage("No result found").build()), 500);
            }

            List<QuestionVO> response = new ArrayList<>();
            for (QuestionEntity question : resultList) {
                QuestionVO questionVO = QuestionVO.childBuilder()
                        .id(question.getId())
                        .body(question.getBody())
                        .status(question.getStatus())
                        .subject(subjectVO).build();
                List<AnswerVO> answerVOList = new ArrayList<>();
                for (AnswerEntity answer : question.getAnswers()) {
                    AnswerVO answerVO = AnswerVO.childBuilder()
                            .body(answer.getBody())
                            .id(answer.getId())
                            .status(answer.getStatus())
                            .build();
                    answerVOList.add(answerVO);
                }

                questionVO.setAnswers(answerVOList);
                response.add(questionVO);
            }
            return new Response<>(new DataVO<>(response), 200);
        } catch (Exception e) {
            return new Response<>(new DataVO<>(AppErrorVO.builder()
                    .friendlyMessage("oops something went wrong")
                    .developerMessage(e.getMessage())
                    .build()), 500);
        }
    }


}
