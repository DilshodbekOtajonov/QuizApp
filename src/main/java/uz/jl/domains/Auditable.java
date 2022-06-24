package uz.jl.domains;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.sql.Timestamp;
import java.util.Objects;


@Getter
@Setter
@NoArgsConstructor
@Builder
@MappedSuperclass
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class Auditable implements BaseDomain {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @CreationTimestamp
    @Column(updatable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private Timestamp createdAt;

    @Column(columnDefinition = "bigint default -1", nullable = false)
    private Long createdBy;

    @Column
    private Timestamp updatedAt;

    private Long updatedBy;

    @Column(columnDefinition = "smallint default 0")
    @Convert(converter = org.hibernate.type.NumericBooleanConverter.class)
    private Boolean deleted;

    public Auditable(Long id, Timestamp createdAt, Long createdBy, Timestamp updatedAt, Long updatedBy, Boolean deleted) {
        this.id = id;
        this.createdAt = createdAt;
        if (Objects.isNull(createdBy))
            createdBy = -1l;
        this.createdBy = createdBy;
        this.updatedAt = updatedAt;
        this.updatedBy = updatedBy;
        this.deleted = deleted;
    }
}
