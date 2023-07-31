package ma.pca.bff.model.entities;

import lombok.*;
import ma.pca.starter.data.model.BaseEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import java.util.Date;

@Entity
@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Example extends BaseEntity<String> {
    private String name;
    @Column(name = "created_at")
    private Date createdAt;
}
