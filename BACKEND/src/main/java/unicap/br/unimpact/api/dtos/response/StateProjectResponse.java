package unicap.br.unimpact.api.dtos.response;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import unicap.br.unimpact.domain.enums.StateEnum;
import unicap.br.unimpact.domain.enums.StatusEnum;

import java.util.Date;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class StateProjectResponse extends DTOResponse {

    private Long id;

    private StateEnum state;

    private StatusProjectResponse actualStatus;

    private Date createdAt;

    private Date modifiedAt;
}
