package unicap.br.unimpact.domain.entities;

import lombok.*;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.util.List;

@Getter
@Setter
@ToString
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "groups")
public class Group extends BaseEntity {

    @NotNull
    private String ownerUserEmail;

    @NotNull(message = "Name is mandatory")
    private String name;

    private String description;

    @OneToMany
    @ToString.Exclude
    private List<PhysicalPerson> members;

    @ElementCollection
    private List<String> areasOfOperation;

}
