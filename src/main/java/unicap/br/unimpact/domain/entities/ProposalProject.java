package unicap.br.unimpact.domain.entities;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
@ToString
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class ProposalProject extends BaseEntity {

    @OneToOne
    private Project project;

    @NotNull
    private String ownerEmail;

    @Size(max = 50000)
    private String description;

}
