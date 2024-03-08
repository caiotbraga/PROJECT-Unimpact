package unicap.br.unimpact.domain.entities;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import java.util.List;


@Getter
@Setter
@ToString
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Responsible extends BaseEntity {

    @OneToOne
    private PhysicalPerson physicalPersonResponsible;

    @ManyToMany
    @ToString.Exclude
    private List<Group> groupsResponsibles;
}
