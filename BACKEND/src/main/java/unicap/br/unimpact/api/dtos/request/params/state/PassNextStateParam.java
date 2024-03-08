package unicap.br.unimpact.api.dtos.request.params.state;

import lombok.Getter;
import lombok.Setter;
import unicap.br.unimpact.domain.enums.StateEnum;

@Getter
@Setter
public class PassNextStateParam {

    private Long projectId;
    private StateEnum nextState;
}
