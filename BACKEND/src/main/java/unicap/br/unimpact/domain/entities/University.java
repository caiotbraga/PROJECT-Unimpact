package unicap.br.unimpact.domain.entities;

import lombok.*;
import org.hibernate.validator.constraints.br.CNPJ;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;
import java.util.List;

@Getter
@Setter
@ToString
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class University extends User {

    @CNPJ
    @NotNull(message = "CNPJ is mandatory")
    private String cnpj;

    @OneToMany(cascade = CascadeType.ALL)
    @ToString.Exclude
    private List<PhysicalPerson> representatives;

}
