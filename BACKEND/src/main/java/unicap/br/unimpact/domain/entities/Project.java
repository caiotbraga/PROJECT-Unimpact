package unicap.br.unimpact.domain.entities;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotNull;
import java.util.List;

@Getter
@Setter
@ToString
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Project extends BaseEntity {

    @NotNull
    private String ownerEmail;

    @OneToOne
    private Responsible responsible;

    @OneToOne
    private StateProject actualState;

    @OneToMany
    @ToString.Exclude
    private List<StateProject> states;

    @OneToOne
    private MetadataProject metadataProject;

    private boolean isActive = true;

    private boolean isNew = true;
}
