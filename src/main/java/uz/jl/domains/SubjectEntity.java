package uz.jl.domains;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

/**
 * @author "Otajonov Dilshodbek
 * @since 6/22/22 6:13 PM (Wednesday)
 * QuizApp/IntelliJ IDEA
 */

@Entity
@Table(schema = "subject",name = "subjects")
@NoArgsConstructor
public class SubjectEntity extends Auditable {
    private String title;

    @Builder(builderMethodName = "childBuilder")
    public SubjectEntity(Long id, Timestamp createdAt, Long createdBy, Timestamp updatedAt, Long updatedBy, Boolean deleted, String title) {
        super(id, createdAt, createdBy, updatedAt, updatedBy, deleted);
        this.title = title;
    }
}
