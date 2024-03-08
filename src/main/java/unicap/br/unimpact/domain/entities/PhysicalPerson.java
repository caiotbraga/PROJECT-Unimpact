package unicap.br.unimpact.domain.entities;

import lombok.*;
import org.hibernate.validator.constraints.br.CPF;
import unicap.br.unimpact.domain.annotations.RG;

import javax.persistence.Entity;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@ToString
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class PhysicalPerson extends User {

    @CPF
    @NotNull(message = "CPF is mandatory")
    private String cpf;

    @RG
    @NotNull(message = "RG is mandatory")
    private String rg;
}
