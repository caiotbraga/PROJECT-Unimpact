package unicap.br.unimpact.api.dtos.response;


import lombok.Data;
import lombok.NoArgsConstructor;
import unicap.br.unimpact.api.dtos.basic.AddressDTO;
import unicap.br.unimpact.api.dtos.basic.PhoneDTO;
import unicap.br.unimpact.domain.entities.PhysicalPerson;
import unicap.br.unimpact.domain.enums.InstitutionTypeEnum;

import java.util.List;


@Data
@NoArgsConstructor
public class GroupResponse extends DTOResponse {

    private Long id;

    private String ownerUserEmail;

    private String name;

    private String description;

    private List<PhysicalPersonResponse> members;

    private List<String> areasOfOperation;
}
