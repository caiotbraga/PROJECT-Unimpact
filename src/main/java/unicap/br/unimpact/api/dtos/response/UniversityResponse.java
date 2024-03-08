package unicap.br.unimpact.api.dtos.response;


import lombok.Data;
import lombok.NoArgsConstructor;
import unicap.br.unimpact.api.dtos.basic.AddressDTO;
import unicap.br.unimpact.api.dtos.basic.PhoneDTO;
import unicap.br.unimpact.domain.enums.InstitutionTypeEnum;

import java.util.List;


@Data
@NoArgsConstructor
public class UniversityResponse extends DTOResponse {

    private Long id;

    private String name;

    private String email;

    private String cnpj;

    private List<PhoneDTO> phones;

    private List<AddressDTO> addresses;
}
