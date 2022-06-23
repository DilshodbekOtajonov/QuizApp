package uz.jl.domains.QA;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import lombok.*;
import uz.jl.domains.Auditable;
import uz.jl.enums.AnswerStatus;

import java.sql.Timestamp;

/**
 * @author "Otajonov Dilshodbek
 * @since 6/22/22 2:39 PM (Wednesday)
 * QuizApp/IntelliJ IDEA
 */

@Entity
@Getter
@ToString
@Table(name = "answers", schema = "question")
@NoArgsConstructor
@AllArgsConstructor
public class AnswerEntity extends Auditable {
    private String body;

    @Enumerated(EnumType.STRING)
    private AnswerStatus status;


    @Builder(builderMethodName = "childBuilder")
    public AnswerEntity(Long id, Timestamp createdAt, Long createdBy, Timestamp updatedAt, Long updatedBy, Boolean deleted, String body, AnswerStatus status) {
        super(id, createdAt, createdBy, updatedAt, updatedBy, deleted);
        this.body = body;
        this.status = status;
    }
}
