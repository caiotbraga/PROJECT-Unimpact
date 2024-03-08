package unicap.br.unimpact.api.dtos.request;


import lombok.*;
import unicap.br.unimpact.api.dtos.basic.AddressDTO;
import unicap.br.unimpact.api.dtos.basic.PhoneDTO;

import java.util.List;


@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PhysicalPersonRequest {

    private String name;

    private String email;

    private String password;

    private String cpf;

    private String rg;

    private List<PhoneDTO> phones;

    private List<AddressDTO> addresses;
}
