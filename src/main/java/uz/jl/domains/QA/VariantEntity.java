package uz.jl.domains.QA;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import uz.jl.domains.Auditable;
import uz.jl.domains.QA.QuestionEntity;
import uz.jl.domains.auth.AuthUser;
import uz.jl.enums.QuestionStatus;

import java.sql.Timestamp;
import java.util.List;

/**
 * @author "Otajonov Dilshodbek
 * @since 6/22/22 2:55 PM (Wednesday)
 * QuizApp/IntelliJ IDEA
 */

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "variants", schema = "test")
public class VariantEntity extends Auditable {
    @OneToOne(targetEntity = AuthUser.class, cascade = CascadeType.MERGE)
    @JoinColumn(name = "user_id")
    private AuthUser user;
    @Enumerated(EnumType.STRING)
    private QuestionStatus status;

    @ManyToMany(targetEntity = QuestionEntity.class,cascade = CascadeType.ALL)
    @JoinTable(name = "variant_question",
            joinColumns = @JoinColumn(name = "variant_id"),
            inverseJoinColumns = @JoinColumn(name = "question_id"),
            schema = "test"
    )
    private List<QuestionEntity> questions;
    @Column(name = "result")
    private Integer numberOfRightAnswers;

    @Builder(builderMethodName = "childBuilder")
    public VariantEntity(Long id, Timestamp createdAt, Long createdBy, Timestamp updatedAt, Long updatedBy, Boolean deleted, AuthUser user, List<QuestionEntity> questions, Integer numberOfRightAnswers) {
        super(id, createdAt, createdBy, updatedAt, updatedBy, deleted);
        this.user = user;
        this.questions = questions;
        this.numberOfRightAnswers = numberOfRightAnswers;
    }
}
