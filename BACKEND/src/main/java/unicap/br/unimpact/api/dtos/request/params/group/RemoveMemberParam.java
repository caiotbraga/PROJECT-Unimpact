package unicap.br.unimpact.api.dtos.request.params.group;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class RemoveMemberParam {

    private Long groupId;
    private String memberEmail;
}
