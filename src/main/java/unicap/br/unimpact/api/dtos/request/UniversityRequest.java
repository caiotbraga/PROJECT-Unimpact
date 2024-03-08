package unicap.br.unimpact.api.dtos.request;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import unicap.br.unimpact.api.dtos.basic.AddressDTO;
import unicap.br.unimpact.api.dtos.basic.PhoneDTO;

import java.util.List;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UniversityRequest {

    private String name;

    private String email;

    private String password;

    private String cnpj;

    private List<PhoneDTO> phones;

    private List<AddressDTO> addresses;
}
