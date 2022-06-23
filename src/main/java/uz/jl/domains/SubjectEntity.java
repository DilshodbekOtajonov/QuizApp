package uz.jl.domains;


import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;

import java.sql.Timestamp;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Table(schema = "subject",name = "Subject")
public class SubjectEntity extends Auditable{
    private String title;

    @Builder(builderMethodName = "childBuilder")
    public SubjectEntity(Long id, Timestamp createdAt, Long createdBy, Timestamp updatedAt, Long updatedBy, Boolean deleted, String title) {
        super(id, createdAt, createdBy, updatedAt, updatedBy, deleted);
        this.title = title;
    }
}
