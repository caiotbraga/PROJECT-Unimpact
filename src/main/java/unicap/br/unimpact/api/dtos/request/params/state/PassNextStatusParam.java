package unicap.br.unimpact.api.dtos.request.params.state;

import lombok.Getter;
import lombok.Setter;
import unicap.br.unimpact.domain.enums.StatusEnum;

@Getter
@Setter
public class PassNextStatusParam {

    private Long stateId;
    private StatusEnum nextStatus;
}
