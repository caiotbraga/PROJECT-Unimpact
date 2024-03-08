package unicap.br.unimpact.api.dtos.response;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import unicap.br.unimpact.api.dtos.response.DTOResponse;
import unicap.br.unimpact.api.dtos.response.GroupResponse;
import unicap.br.unimpact.api.dtos.response.PhysicalPersonResponse;

import java.util.List;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ResponsibleResponse extends DTOResponse {

    private Long id;

    private PhysicalPersonResponse physicalPersonResponsible;

    private List<GroupResponse> groupsResponsibles;
}
