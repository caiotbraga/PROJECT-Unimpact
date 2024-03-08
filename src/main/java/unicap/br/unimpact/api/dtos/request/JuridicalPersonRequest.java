package unicap.br.unimpact.api.dtos.request;


import lombok.*;
import unicap.br.unimpact.api.dtos.basic.AddressDTO;
import unicap.br.unimpact.api.dtos.basic.PhoneDTO;
import unicap.br.unimpact.domain.enums.InstitutionTypeEnum;

import java.util.List;


@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class JuridicalPersonRequest {

    private String name;

    private String email;

    private String password;

    private String cnpj;

    private String fantasyName;

    private InstitutionTypeEnum institutionType;

    private List<PhoneDTO> phones;

    private List<AddressDTO> addresses;
}
