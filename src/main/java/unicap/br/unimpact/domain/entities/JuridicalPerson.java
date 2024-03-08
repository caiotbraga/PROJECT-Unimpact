package unicap.br.unimpact.domain.entities;

import lombok.*;
import org.hibernate.validator.constraints.br.CNPJ;
import unicap.br.unimpact.domain.enums.InstitutionTypeEnum;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;

@Getter
@Setter
@ToString
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class JuridicalPerson extends User {

    @CNPJ
    @NotNull(message = "CNPJ is mandatory")
    private String cnpj;

    private String fantasyName;

    @Enumerated(EnumType.STRING)
    private InstitutionTypeEnum institutionType;

    @OneToMany(cascade = CascadeType.ALL)
    @ToString.Exclude
    private List<PhysicalPerson> representatives;

}
