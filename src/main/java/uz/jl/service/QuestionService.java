package uz.jl.service;

import jakarta.transaction.Transactional;
import lombok.NonNull;
import uz.jl.configs.ApplicationContextHolder;
import uz.jl.dao.AbstractDAO;
import uz.jl.dao.qa.QuestionDAO;
import uz.jl.domains.QA.QuestionEntity;
import uz.jl.domains.SubjectEntity;
import uz.jl.enums.QuestionStatus;
import uz.jl.utils.BaseUtils;
import uz.jl.utils.validators.question.QuestionValidator;
import uz.jl.vo.http.AppErrorVO;
import uz.jl.vo.http.DataVO;
import uz.jl.vo.http.Response;
import uz.jl.vo.question.QuestionCreateVO;
import uz.jl.vo.question.QuestionUpdateVO;
import uz.jl.vo.question.QuestionVO;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class QuestionService extends AbstractDAO<QuestionDAO> implements GenericCRUDService<QuestionVO, QuestionCreateVO, QuestionUpdateVO, Long> {
    private static QuestionService instance;

    private final QuestionValidator questionValidator = ApplicationContextHolder.getBean(QuestionValidator.class);

    private QuestionService() {
        super(
                ApplicationContextHolder.getBean(QuestionDAO.class),
                ApplicationContextHolder.getBean(BaseUtils.class));
    }

    @Override
    @Transactional
    public Response<DataVO<Long>> create(@NonNull QuestionCreateVO vo) {
        return null;
    }

    @Override
    public Response<DataVO<Void>> update(@NonNull QuestionUpdateVO vo) {
        return null;
    }

    @Override
    public Response<DataVO<Void>> delete(@NonNull Long aLong) {
        return null;
    }

    @Override
    public Response<DataVO<QuestionVO>> get(@NonNull Long aLong) {
        return null;
    }

    @Override
    public Response<DataVO<List<QuestionVO>>> getAll() {
        return null;
    }

    public Response<DataVO<List<QuestionEntity>>> getAll(String name, QuestionStatus level) {
        List<QuestionEntity> response = new ArrayList<>();

        Optional<SubjectEntity> _subject = dao.findByName(name);
        if (_subject.isEmpty()) {
            return new Response<>(new DataVO<>(AppErrorVO.builder()
                    .friendlyMessage("No subject with name '%s'".formatted(name)).build()));
        }
        SubjectEntity subjectEntity = _subject.get();
        Long id = subjectEntity.getId();

        List<QuestionEntity> resultList;
        if (Objects.isNull(level))
            resultList = dao.findAll(id);
        else
            resultList = dao.findAll(id, level);

        if (resultList.isEmpty()) {
            return new Response<>(new DataVO<>(AppErrorVO.builder()
                    .friendlyMessage("No").build()));
        }
        for (QuestionEntity question : resultList) {
            QuestionEntity questionEntity = QuestionEntity.childBuilder()
                    .id(question.getId())
                    .body(question.getBody())
                    .status(question.getStatus())
                    .answers(question.getAnswers())
                    .subject(question.getSubject()).build();
            response.add(questionEntity);
        }
        return new Response<>(new DataVO<>(response), 200);
    }



    public static QuestionService getInstance() {
        if (instance == null) {
            instance = new QuestionService();
        }
        return instance;
    }
}
