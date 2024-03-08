package unicap.br.unimpact.domain.entities;

import lombok.*;
import unicap.br.unimpact.domain.annotations.Cep;

import javax.persistence.Entity;

@Getter
@Setter
@ToString
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Address extends BaseEntity {

    private String street;

    private String number;

    private String district;

    private String city;

    private String state;

    private String country;

    @Cep
    private String cep;
}
