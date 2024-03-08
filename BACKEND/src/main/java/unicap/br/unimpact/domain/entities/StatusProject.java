package unicap.br.unimpact.domain.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import unicap.br.unimpact.domain.enums.StatusEnum;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;

@Getter
@Setter
@ToString
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class StatusProject extends BaseEntity {

    @ManyToOne
    @JsonIgnore
    @ToString.Exclude
    private StateProject state;

    @NotNull
    @Enumerated(EnumType.STRING)
    private StatusEnum status;

    @OneToMany
    @ToString.Exclude
    private List<Feedback> feedbacks;

    @OneToMany
    @ToString.Exclude
    private List<MetadataProject> historyMetadataProject;

    @ManyToMany
    @ToString.Exclude
    private List<Group> departments;

    @OneToOne
    private PhysicalPerson middleEmployee;
}
