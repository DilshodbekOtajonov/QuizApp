package uz.jl.service;

import lombok.NonNull;
import uz.jl.configs.ApplicationContextHolder;

import uz.jl.dao.AbstractDAO;
import uz.jl.dao.qa.QuestionDAO;
import uz.jl.domains.QA.AnswerEntity;
import uz.jl.domains.QA.QuestionEntity;
import uz.jl.utils.BaseUtils;

import uz.jl.vo.answer.AnswerCreateVO;
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

public class QuestionService extends AbstractDAO<QuestionDAO> implements GenericCRUDService<
        QuestionVO,
        QuestionCreateVO,
        QuestionUpdateVO,
        Long> {

    private static QuestionService instance;

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
        List<AnswerEntity> answerList = new ArrayList<>();

        for (AnswerCreateVO answer : vo.getAnswers()) {
            AnswerEntity build = AnswerEntity.childBuilder()
                    .body(answer.getBody())
                    .status(answer.getStatus())
                    .build();
            answerList.add(build);
        }
        QuestionEntity question = QuestionEntity.childBuilder()
                .body(vo.getBody())
                .answers(answerList)
                .status(vo.getStatus())
                .build();
        QuestionEntity save = dao.save(question);

        return new Response<>(new DataVO<>(save.getId()), 200);
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
                    .build()));
        }
        List<QuestionVO> response = new ArrayList<>();
        for (QuestionEntity questionEntity : all) {
            QuestionVO questionVO = QuestionVO.childBuilder()
                    .body(questionEntity.getBody())
                    .answers(questionEntity.getAnswers())
                    .id(questionEntity.getId())
                    .status(questionEntity.getStatus())
                    .build();
            response.add(questionVO);
        }

        return new Response<>(new DataVO<>(response));
    }
}
;